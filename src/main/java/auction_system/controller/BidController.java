package auction_system.controller;

import auction_system.entity.Bid;
import auction_system.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/bids")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping
    public ResponseEntity<Bid> placeBid(@RequestParam Long auctionId,
                                        @RequestParam Long bidderId,
                                        @RequestParam BigDecimal amount) {
        Bid bid = bidService.placeBid(auctionId, bidderId, amount);
        return ResponseEntity.ok(bid);
    }

    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<Bid>> getBids(@PathVariable Long auctionId) {
        return ResponseEntity.ok(bidService.getBidsByAuction(auctionId));
    }
}