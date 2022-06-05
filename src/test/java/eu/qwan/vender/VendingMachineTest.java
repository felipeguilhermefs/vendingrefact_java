package eu.qwan.vender;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {
	private final VendingMachine machine = new VendingMachine();

	@Test
	public void Testchoiceless_machine_delivers_nothing() {
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.cola));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.fanta));
	}

	@Test
	public void Testdelivers_can_of_choice() {
		machine.configure(Choice.cola, Can.cola, 10);
		machine.configure(Choice.fanta, Can.fanta, 10);
		machine.configure(Choice.sprite, Can.sprite, 10);
		assertEquals(Optional.of(Can.cola), machine.deliverCan(Choice.cola));
		assertEquals(Optional.of(Can.fanta), machine.deliverCan(Choice.fanta));
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testdelivers_nothing_when_making_invalid_choice() {
		machine.configure(Choice.cola, Can.cola, 10);
		machine.configure(Choice.fanta, Can.fanta, 10);
		machine.configure(Choice.sprite, Can.sprite, 10);
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.beer));
	}

	@Test
	public void Testdelivers_nothing_when_not_paid() {
		machine.configure(Choice.fanta, Can.fanta, 10, 2);
		machine.configure(Choice.sprite, Can.sprite, 10, 1);

		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.fanta));
	}

	@Test
	public void Testdelivers_fanta_when_paid() {
		machine.configure(Choice.sprite, Can.sprite, 10, 1);
		machine.configure(Choice.fanta, Can.fanta, 10, 2);

		machine.setValue(2);
		assertEquals(Optional.of(Can.fanta), machine.deliverCan(Choice.fanta));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testdelivers_sprite_when_paid() {
		machine.configure(Choice.sprite, Can.sprite, 10, 1);
		machine.configure(Choice.fanta, Can.fanta, 10, 2);

		machine.setValue(2);
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testadd_payments() {
		machine.configure(Choice.sprite, Can.sprite, 10, 1);
		machine.configure(Choice.fanta, Can.fanta, 10, 2);

		machine.setValue(1);
		machine.setValue(1);
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testreturns_change() {
		machine.configure(Choice.sprite, Can.sprite, 10, 1);
		machine.setValue(2);
		assertEquals(2, machine.getChange());
		assertEquals(0, machine.getChange());
	}

	@Test
	public void Teststock() {
		machine.configure(Choice.sprite, Can.sprite, 1, 0);
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testadd_stock() {
		machine.configure(Choice.sprite, Can.sprite, 1, 0);
		machine.configure(Choice.sprite, Can.sprite, 1, 0);
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
	}

	@Test
	public void Testcheckout_chip_if_chipknip_inserted() {
		machine.configure(Choice.sprite, Can.sprite, 1, 1);
		ChipknipWallet chip = new ChipknipWallet(10);
		machine.insertChip(chip);
		assertEquals(Optional.of(Can.sprite), machine.deliverCan(Choice.sprite));
		assertEquals(9, chip.credits);
	}

	@Test
	public void Testcheckout_chip_empty() {
		machine.configure(Choice.sprite, Can.sprite, 1, 1);
		ChipknipWallet chip = new ChipknipWallet(0);
		machine.insertChip(chip);
		assertEquals(Optional.of(Can.none), machine.deliverCan(Choice.sprite));
		assertEquals(0, chip.credits);
	}
}
