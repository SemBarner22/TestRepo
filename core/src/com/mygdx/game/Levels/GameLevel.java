package com.mygdx.game.Levels;

import java.util.Locale;

public class GameLevel {
    public static class TransitGroup {
        public int groupSize, groupMeanY;
        public boolean right;

        public TransitGroup(int groupSize, int groupMeanY, boolean right) {
            this.groupSize = groupSize;
            this.groupMeanY = groupMeanY;
            this.right = right;
        }
    }

    static String intelFormatNone = "There are not any expected large formations transitions expected.";
    static String intelFormat = "Our intel reports that the large fleets will be transiting the inlet near these y-coords. Keep away!";
    public String intel;
    public String briefing;
    public int patrolCount;
    public static int finalLevel = 9;

    public TransitGroup[] transitGroups;

    public GameLevel(int level) {
        switch (level) {
            case 1: {
                briefing = "Soldier! The situation is desperate. Our frontliners have been cut off by the enemy naval blockade. It is crucial to keep the supply line and evacuate the wounded soldiers. Breach through the blockade and reach any of our bridgeheads on the other coast. You will unload the supplies and load our fellow soldiers there. Also, be aware of cliffs, we are not skilled enough to avoid them. Stay safe and come back home alive!";
                patrolCount = 10;
                transitGroups = new TransitGroup[0];
                intel = intelFormatNone;
                break;
            }
            case 2: {
                briefing = "Glad you've made it here! We may survive one more day here. Return to base ASAP! Choose the one you are going to return to.";
                patrolCount = 100;
                transitGroups = new TransitGroup[0];
                intel = intelFormatNone;
                break;
            }
            case 3: {
                briefing = "These soldiers owe you a lot. You've made a significant impact on out situation here. But the enemy didn't like this: they're sending reinforcements to strengthen the blockade. Stay away from patrols!";
                patrolCount = 50;
                transitGroups = new TransitGroup[0];
                intel = intelFormatNone;
                break;
            }
            case 4: {
                briefing = "You've made it here again, thank you, soldier! Tell the command that we're doing our best here, but without this supply line there are no chances to see the homeland again.";
                patrolCount = 60;
                transitGroups = new TransitGroup[0];
                intel = intelFormatNone;
                break;
            }
            case 5: {
                briefing = "Soldier! Bad new! The enemy is sending even more reinforcements against you. Besides, we have reports that large enemy groups have started using the inlet. Refer to the intel to avoid contacts!";
                patrolCount = 70;
                transitGroups = new TransitGroup[] {
                        new TransitGroup(5, 30, true),
                        new TransitGroup(5, 66, false),
                };
                buildIntel();
                break;
            }
            case 6: {
                briefing = "Finally, sandwich... Make sure my soldiers come back home safe.";
                patrolCount = 80;
                transitGroups = new TransitGroup[] {
                        new TransitGroup(5, 25, true),
                        new TransitGroup(5, 64, false),
                        new TransitGroup(5, 88, true),
                };
                buildIntel();
                break;
            }
            case 7: {
                briefing = "We finally have some good news! Our frontliners report significant victories on the other coast! But they will be overrun and all this would be for nothing unless they are supplied. The enemy knows that, so expect as many patrols as you have never seen before. Godspeed!";
                patrolCount = 100;
                transitGroups = new TransitGroup[] {
                        new TransitGroup(10, 30, true),
                        new TransitGroup(10, 100, false),
                        new TransitGroup(10, 44, true),
                        new TransitGroup(10, 68, false),
                        new TransitGroup(10, 70, true),
                };
                buildIntel();
                break;
            }
            case 8: {
                briefing = "We knew you could do that! You are a hero! But there are still other heroes those need immediate treat. We won't consider this operation a success unless you get home safe. Good luck!";
                patrolCount = 100;
                transitGroups = new TransitGroup[] {
                        new TransitGroup(10, 40, true),
                        new TransitGroup(10, 50, false),
                        new TransitGroup(10, 60, false),
                        new TransitGroup(10, 80, true),
                        new TransitGroup(10, 90, false),
                };
                buildIntel();
                break;
            }
        }
    }

    private void buildIntel() {
        StringBuilder intelBuilder = new StringBuilder(intelFormat);
        for (TransitGroup transitGroup : transitGroups) {
            intelBuilder.append(String.format(Locale.US, "Group of est. size %d near y-coord %d, %s.%n", transitGroup.groupSize, transitGroup.groupMeanY, transitGroup.right ? "due West" : "due East"));
        }
        intel = intelBuilder.toString();
    }

    public String getBriefing() {
        return this.briefing + String.format("%n%n") + this.intel;
    }
}