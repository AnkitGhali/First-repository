import java.util.Scanner;

public class p5 {

    private static void printWindow(int base, int end) {
        System.out.print("Sender Window : [ ");

        for (int i = base; i <= end; i++) {
            System.out.print(i);

            if (i < end) {
                System.out.print(" | ");
            }
        }

        System.out.println(" ]");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===============================================");
        System.out.println("     GO-BACK-N SLIDING WINDOW PROTOCOL");
        System.out.println("===============================================");

        System.out.print("Enter total number of frames : ");
        int totalFrames = sc.nextInt();

        System.out.print("Enter window size : ");
        int windowSize = sc.nextInt();

        System.out.print("Frame to lose once (0 = none): ");
        int lossFrame = sc.nextInt();

        // Check input
        if (totalFrames <= 0 ||
            windowSize <= 0 ||
            windowSize > totalFrames ||
            lossFrame < 0 ||
            lossFrame > totalFrames) {

            System.out.println("Invalid input. Please check the values.");
            sc.close();
            return;
        }

        int base = 1;
        int expectedFrame = 1;
        boolean lossAlreadySimulated = false;
        int round = 1;

        System.out.println();
        System.out.println("Simulation started...");
        System.out.println();

        while (base <= totalFrames) {

            int end = Math.min(base + windowSize - 1, totalFrames);

            System.out.println("---------------- ROUND " + round + " ----------------");

            printWindow(base, end);

            // Send all frames in the current window
            for (int frame = base; frame <= end; frame++) {

                System.out.println(
                    "Sender -> Receiver : FRAME " + frame
                );

                // Simulate frame loss
                if (frame == lossFrame && !lossAlreadySimulated) {

                    System.out.println(
                        "X FRAME " + frame + " LOST IN TRANSIT"
                    );

                    lossAlreadySimulated = true;
                    continue;
                }

                // Correct frame received
                if (frame == expectedFrame) {

                    System.out.println(
                        "Receiver : FRAME " + frame + " accepted"
                    );

                    System.out.println(
                        "Receiver -> Sender : ACK " + frame
                    );

                    expectedFrame++;

                } else {

                    System.out.println(
                        "Receiver : FRAME " + frame
                        + " discarded (expected FRAME "
                        + expectedFrame + ")"
                    );

                    System.out.println(
                        "Receiver -> Sender : Duplicate ACK "
                        + (expectedFrame - 1)
                    );
                }
            }

            // Check whether retransmission is required
            if (expectedFrame <= end) {

                System.out.println();
                System.out.println(
                    "TIMEOUT! ACK for FRAME "
                    + expectedFrame
                    + " not received."
                );

                System.out.println(
                    "Go-Back-N action: retransmit from FRAME "
                    + expectedFrame + "."
                );

                // Move base back to missing frame
                base = expectedFrame;

            } else {

                // All frames in current window acknowledged
                base = end + 1;

                if (base <= totalFrames) {

                    System.out.println();
                    System.out.println(
                        "All frames in this window acknowledged."
                    );

                    System.out.println(
                        "Window slides forward to FRAME "
                        + base + "."
                    );
                }
            }

            System.out.println();
            round++;
        }

        System.out.println("===============================================");
        System.out.println(
            "All " + totalFrames + " frames delivered successfully."
        );
        System.out.println(
            "Sliding Window transmission completed."
        );
        System.out.println("===============================================");

        sc.close();
    }
}

