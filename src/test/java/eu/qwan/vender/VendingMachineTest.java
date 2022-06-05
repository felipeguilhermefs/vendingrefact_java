package eu.qwan.vender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    private final VendingMachine machine = new VendingMachine();

    @Nested
    class ChoicelessMachine {

        @Test
        public void deliversNothing() {
            for (Choice choice : Choice.values()) {
                assertEquals(Optional.empty(), machine.deliver(choice));
            }
        }
    }

    @Nested
    class FreeMachine {

        @BeforeEach
        public void setup() {
            machine.configure(Choice.COLA, Can.COLA, 1);
            machine.configure(Choice.FANTA, Can.FANTA, 1);
            machine.configure(Choice.SPRITE, Can.SPRITE, 1);
        }

        @Test
        public void deliversColaWhenColaIsChosen() {
            assertEquals(Optional.of(Can.COLA), machine.deliver(Choice.COLA));
        }

        @Test
        public void deliversFantaWhenFantaIsChosen() {
            assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        }

        @Test
        public void deliversSpriteWhenSpriteIsChosen() {
            assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        }

        @Test
        public void deliversNothingWhenInvalidChoice() {
            assertEquals(Optional.empty(), machine.deliver(Choice.BEER));
        }
    }

    @Nested
    class PricedMachine {

        @BeforeEach
        public void setup() {
            machine.configure(Choice.FANTA, Can.FANTA, 10, 2);
            machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);
        }

        @Test
        public void deliversNothingWhenNotPaid() {
            assertEquals(Optional.empty(), machine.deliver(Choice.FANTA));
        }

        @Test
        public void deliversFantaWhenPaidExactAmount() {
            machine.insertCredits(2);

            assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        }

        @Test
        public void deliversFantaWhenPaidMoreThanRequired() {
            machine.insertCredits(3);

            assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        }

        @Test
        public void deliversAsManyFantasAsPaidFor() {
            machine.insertCredits(2);

            assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
            assertEquals(Optional.empty(), machine.deliver(Choice.FANTA));
        }

        @Test
        public void deliversAsManySpritesAsPaidFor() {
            machine.insertCredits(2);

            assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
            assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
            assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
        }

        @Test
        public void deliversFantaAfterPaymentIsEnough() {
            machine.insertCredits(1);
            machine.insertCredits(1);

            assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        }

        @Test
        public void returnsChangeAfterBuyingFanta() {
            machine.insertCredits(3);

            machine.deliver(Choice.FANTA);

            assertEquals(1, machine.getChange());
        }

        @Test
        public void returnsNoChangeWhenUsedAllMoney() {
            machine.insertCredits(2);

            machine.deliver(Choice.FANTA);

            assertEquals(0, machine.getChange());
        }

        @Test
        public void returnsNoChangeWhenAskedASecondTime() {
            machine.insertCredits(3);

            machine.deliver(Choice.FANTA);

            machine.getChange();
            assertEquals(0, machine.getChange());
        }
    }

    @Nested
    class LimitedStockMachine {

        @BeforeEach
        public void setup() {
            machine.configure(Choice.SPRITE, Can.SPRITE, 1);
        }

        @Test
        public void deliversNothingWhenStockIsDepleted() {
            machine.deliver(Choice.SPRITE);

            assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
        }

        @Test
        public void deliversAfterStockIsAdded() {
            machine.deliver(Choice.SPRITE);
            machine.configure(Choice.SPRITE, Can.SPRITE, 1);

            assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        }
    }

    @Nested
    class ChipPaidMachine {

        @BeforeEach
        public void setup() {
            machine.configure(Choice.SPRITE, Can.SPRITE, 1, 1);
        }

        @Test
        public void payWithChipWhenChipIsInserted() {
            var chip = new Chipknip(10);
            machine.insertChip(chip);

            assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
            assertEquals(9, chip.credits);
        }

        @Test
        public void deliversNothingWhenChipIsEmpty() {
            var chip = new Chipknip(0);
            machine.insertChip(chip);

            assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
            assertEquals(0, chip.credits);
        }
    }
}
