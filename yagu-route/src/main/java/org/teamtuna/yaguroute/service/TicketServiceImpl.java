package org.teamtuna.yaguroute.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.teamtuna.yaguroute.aggregate.Ticket;
import org.teamtuna.yaguroute.aggregate.Member;
import org.teamtuna.yaguroute.aggregate.GameSeat;
import org.teamtuna.yaguroute.dto.GameDetailDTO;
import org.teamtuna.yaguroute.dto.MailDTO;
import org.teamtuna.yaguroute.dto.MemberDTO;
import org.teamtuna.yaguroute.dto.TicketDTO;
import org.teamtuna.yaguroute.repository.TicketRepository;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final MemberService memberService;
    private final GameSeatServiceImpl gameSeatService;
    private final GameService gameService;
    private final TicketRepository ticketRepository;

    private final KafkaTemplate<String, MailDTO> kafkaTemplate;
    private static final String TOPIC = "tickets";
    private static final String DLQ = "email-dlq";

    public Ticket converToTicket(TicketDTO ticketDTO) {
        return ticketRepository.findById(ticketDTO.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
    }

    public TicketDTO getTicketById(int ticketId) {
        return new TicketDTO(ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException("Ticket not found")));
    }

    @Transactional
    public TicketDTO bookTicket(int memberId, int gameId, int seatId) {
        if (!gameSeatService.isSeatOccupied(gameId, seatId)) {
            Member member = memberService.getMemberById(memberId).orElseThrow(() -> new EntityNotFoundException("Member Not Found"));
            GameSeat gameSeat = gameSeatService.convertToGameSeat(gameSeatService.getTicketByGameIdAndSeatId(gameId, seatId));

            Ticket ticket = new Ticket(0, null, gameSeat.getGameSeatPrice(), member, gameSeat);
            TicketDTO result = new TicketDTO(ticketRepository.save(ticket));

            gameSeatService.occupySeat(gameId, seatId);
            getMailInfo(memberId, ticket.getTicketId());

            return result;
        }
        throw new RuntimeException("예매 불가");
    }

    public void getMailInfo(int memberId, int ticketId) {
        MemberDTO member = new MemberDTO(memberService.getMemberById(memberId).orElseThrow(() -> new EntityNotFoundException("Member not found.")));
        TicketDTO ticket = getTicketById(ticketId);
        GameDetailDTO detail = gameService.getGameDetailsByGameSeatId(ticket.getGameSeatId());

        MailDTO mailInfo = new MailDTO(member.getMemberEmail(), member.getMemberName(), String.valueOf(ticket.getTicketDate()), detail.getGameDate(), detail.getGameTime(), detail.getLocation(), detail.getStadiumName(), detail.getSeatNum(), detail.getHomeTeamName(), detail.getAwayTeamName());

        kafkaTemplate.send(TOPIC, mailInfo);

//        CompletableFuture<SendResult<String, MailDTO>> future = kafkaTemplate.send(TOPIC, mailInfo);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                RecordMetadata metadata = result.getRecordMetadata();
//            } else {
//                kafkaTemplate.send(DLQ, mailInfo);
//            }
//        });
    }
}
