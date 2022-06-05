package eu.qwan.vender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    private final VendingMachine machine = new VendingMachine();

    @Test
    public void choicelessMachineDeliversNothing() {
        assertEquals(Optional.empty(), machine.deliver(Choice.COLA));
        assertEquals(Optional.empty(), machine.deliver(Choice.FANTA));
    }

    @Test
    public void deliversCanOfChoice() {
        machine.configure(Choice.COLA, Can.COLA, 10);
        machine.configure(Choice.FANTA, Can.FANTA, 10);
        machine.configure(Choice.SPRITE, Can.SPRITE, 10);
        assertEquals(Optional.of(Can.COLA), machine.deliver(Choice.COLA));
        assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void deliversNothingWhenMakingInvalidChoice() {
        machine.configure(Choice.COLA, Can.COLA, 10);
        machine.configure(Choice.FANTA, Can.FANTA, 10);
        machine.configure(Choice.SPRITE, Can.SPRITE, 10);
        assertEquals(Optional.empty(), machine.deliver(Choice.BEER));
    }

    @Test
    public void deliversNothingWhenNotPaid() {
        machine.configure(Choice.FANTA, Can.FANTA, 10, 2);
        machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);

        assertEquals(Optional.empty(), machine.deliver(Choice.FANTA));
    }

    @Test
    public void deliversFantaWhenPaid() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);
        machine.configure(Choice.FANTA, Can.FANTA, 10, 2);

        machine.insertCredits(2);
        assertEquals(Optional.of(Can.FANTA), machine.deliver(Choice.FANTA));
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void deliversSpriteWhenPaid() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);
        machine.configure(Choice.FANTA, Can.FANTA, 10, 2);

        machine.insertCredits(2);
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void addPayments() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);
        machine.configure(Choice.FANTA, Can.FANTA, 10, 2);

        machine.insertCredits(1);
        machine.insertCredits(1);
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void returnsChange() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 10, 1);
        machine.insertCredits(2);
        assertEquals(2, machine.getChange());
        assertEquals(0, machine.getChange());
    }

    @Test
    public void stock() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 1, 0);
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void addStock() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 1, 0);
        machine.configure(Choice.SPRITE, Can.SPRITE, 1, 0);
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
    }

    @Test
    public void checkoutChipIfChipknipInserted() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 1, 1);
        Chipknip chip = new Chipknip(10);
        machine.insertChip(chip);
        assertEquals(Optional.of(Can.SPRITE), machine.deliver(Choice.SPRITE));
        assertEquals(9, chip.credits);
    }

    @Test
    public void checkoutChipEmpty() {
        machine.configure(Choice.SPRITE, Can.SPRITE, 1, 1);
        Chipknip chip = new Chipknip(0);
        machine.insertChip(chip);
        assertEquals(Optional.empty(), machine.deliver(Choice.SPRITE));
        assertEquals(0, chip.credits);
    }
}
