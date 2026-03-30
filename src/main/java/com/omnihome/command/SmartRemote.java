package com.omnihome.command;

/**
 * Part 3 – Command Pattern: Invoker
 * pressUndo() reverses the single most-recent action (LIFO)
 */
public class SmartRemote {

    private static final int SLOTS = 10;

    private final Command[] buttons = new Command[SLOTS];
    private Command lastCommand = null;   // tracks most-recent for undo

    /**
     * Programs a button slot with a command.
     *
     * @param slot    button index (0 – 9)
     */
    public void setCommand(int slot, Command command) {
        if (slot < 0 || slot >= SLOTS) {
            throw new IllegalArgumentException("Slot must be 0–" + (SLOTS - 1));
        }
        buttons[slot] = command;
        System.out.println("[SmartRemote] 🔧 Button " + slot + " programmed with " + command.getClass().getSimpleName());
    }


    public void pressButton(int slot) {
        if (slot < 0 || slot >= SLOTS || buttons[slot] == null) {
            System.out.println("[SmartRemote] ⚠️  No command assigned to button " + slot);
            return;
        }
        System.out.println("[SmartRemote] ▶ Pressing button " + slot + "...");
        buttons[slot].execute();
        lastCommand = buttons[slot];
    }


//     Reverses the very last action taken by the remote.

    public void pressUndo() {
        if (lastCommand == null) {
            System.out.println("[SmartRemote] ⚠️  Nothing to undo.");
            return;
        }
        System.out.println("[SmartRemote] ↩ Pressing UNDO...");
        lastCommand.undo();
        lastCommand = null; // one-level undo
    }
}
