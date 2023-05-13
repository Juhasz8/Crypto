package com.example.poof_ui;
import java.util.*;

// Need to create separate lists for each category and pick a random one from the chosen list.

public class CurrentEventManager {
    private CurrentEvent event_1 = new CurrentEvent("A major government adopts the cryptocurrency as its official currency.", 5);
    private CurrentEvent event_2 = new CurrentEvent("A significant vulnerability is discovered in the cryptocurrency's underlying technology.", 5);
    private CurrentEvent event_3 = new CurrentEvent("The cryptocurrency is recognized as a global reserve currency by a major financial institution.", 5);
    private CurrentEvent event_4 = new CurrentEvent("A prominent celebrity publicly endorses the cryptocurrency.", 15);
    private CurrentEvent event_5 = new CurrentEvent("Regulatory authorities introduce favorable regulations for the cryptocurrency.", 15);
    private CurrentEvent event_6 = new CurrentEvent("A major merchant starts accepting the cryptocurrency as a form of payment.", 15);
    private CurrentEvent event_7 = new CurrentEvent("The cryptocurrency's development team announces a breakthrough technological advancement.", 15);
    private CurrentEvent event_8 = new CurrentEvent("A significant security breach occurs in a major cryptocurrency exchange.", 30);
    private CurrentEvent event_9 = new CurrentEvent("A large-scale hack targets multiple wallets holding the cryptocurrency.", 30);
    private CurrentEvent event_10 = new CurrentEvent("The cryptocurrency's community successfully implements a major upgrade.", 30);
    private CurrentEvent event_11 = new CurrentEvent("The cryptocurrency experiences a surge in adoption in a specific geographic region.", 30);
    private CurrentEvent event_12 = new CurrentEvent("Negative media coverage highlights the involvement of the cryptocurrency in illicit activities.", 30);
    private CurrentEvent event_13 = new CurrentEvent("A market-wide correction leads to a temporary decline in the cryptocurrency's price.",50);
    private CurrentEvent event_14 = new CurrentEvent("A competing cryptocurrency gains substantial popularity and market share.", 50);
    private CurrentEvent event_15 = new CurrentEvent("Economic instability in a major country prompts investors to seek alternative assets.", 50);
    private CurrentEvent event_16 = new CurrentEvent("The cryptocurrency's development team faces internal conflicts and slows down progress.", 50);
    private CurrentEvent event_17 = new CurrentEvent("A prominent regulatory body issues a warning or imposes restrictions on the cryptocurrency.", 50);
    List<CurrentEvent> eventList = new ArrayList<>();

    // Get an event
    public CurrentEvent getEvent() {
        // Add the events to a list
        eventList.add(event_1);
        eventList.add(event_2);
        eventList.add(event_3);
        eventList.add(event_4);
        eventList.add(event_5);
        eventList.add(event_6);
        eventList.add(event_7);
        eventList.add(event_8);
        eventList.add(event_9);
        eventList.add(event_10);
        eventList.add(event_11);
        eventList.add(event_12);
        eventList.add(event_13);
        eventList.add(event_14);
        eventList.add(event_15);
        eventList.add(event_16);
        eventList.add(event_17);

        Map<Integer, List<Integer>> dictionary = Map.of(
                5, List.of(0, 6), // Key 5 corresponds to the range [0, 6]
                15, List.of(6, 21),  // Key 15 corresponds to the range [6, 21]
                30, List.of(21, 51), // Key 30 corresponds to the range [21, 51]
                50, List.of(51, 100) // Key 50 corresponds to the range [51, 100]
        );
        Random randomEventNumber = new Random();
        int myRandomEventNumber = randomEventNumber.nextInt(101); // Generate a random event number between 0 and 100 (inclusive)
        int myKey = 0;

        // Iterate through the dictionary entries
        for (Map.Entry<Integer, List<Integer>> entry : dictionary.entrySet()) {
            String key = String.valueOf(entry.getKey());
            List<Integer> value = entry.getValue();

            // Check if the random event number falls within the range of the current dictionary entry
            if (myRandomEventNumber > value.get(0) && myRandomEventNumber < value.get(1)) {
                myKey = Integer.parseInt(key); // Set myKey to the corresponding key value
            }
        }

        // Return the event based on the matched myKey value
        for (CurrentEvent event : eventList) {
            if (event.getProbability() == myKey) {
                return event;
            }
        }


        return null; // Return null if no matching event is found (It will always find haha)
    }
}
