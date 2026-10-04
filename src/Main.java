import bridge.device.RadioDevice;
import bridge.device.TvDevice;
import bridge.device.ProjectorDevice;
import bridge.remote.BasicRemote;
import bridge.remote.QuietRemote;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equals(args[0])) {
            runDemo();
            return;
        }

        System.out.println("Usage: java -cp out Main --demo");
    }

    private static void runDemo() {
        int passed = 0;
        int total = 7;

        if (runT1()) {
            passed++;
        }

        if (runT2()) {
            passed++;
        }

        if (runT3()) {
            passed++;
        }

        if (runT4()) {
            passed++;
        }

        if (runT5()) {
            passed++;
        }

        if (runT6()){
            passed++;
        }

        if (runT7()){
            passed++;
        }

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
    }

    private static boolean runT1() {
        BasicRemote remote =
                new BasicRemote("BASIC-01", new TvDevice());

        String actual = remote.execute();
        String expected = "TV | power=ON | volume=30";

        return printResult(
                "T1",
                "BasicRemote + TvDevice",
                actual,
                expected
        );
    }

    private static boolean runT2() {
        BasicRemote remote =
                new BasicRemote("BASIC-01", new RadioDevice());

        String actual = remote.execute();
        String expected = "RADIO | power=ON | volume=30";

        return printResult(
                "T2",
                "BasicRemote + RadioDevice",
                actual,
                expected
        );
    }

    private static boolean runT3() {
        QuietRemote remote =
                new QuietRemote("QUIET-01", new TvDevice());

        String actual = remote.execute();
        String expected = "TV | power=ON | volume=5";

        return printResult(
                "T3",
                "QuietRemote + TvDevice",
                actual,
                expected
        );
    }

    private static boolean runT4() {
        QuietRemote remote =
                new QuietRemote("QUIET-01", new RadioDevice());

        String actual = remote.execute();
        String expected = "RADIO | power=ON | volume=5";

        return printResult(
                "T4",
                "QuietRemote + RadioDevice",
                actual,
                expected
        );
    }

    private static boolean runT5() {
        BasicRemote remote =
                new BasicRemote("BASIC-SWITCH", new TvDevice());

        BasicRemote originalReference = remote;

        String originalId = remote.getId();
        int originalVolumePreset = remote.getVolumePreset();

        String before = remote.execute();
        String expectedBefore = "TV | power=ON | volume=30";

        remote.setImplementation(new RadioDevice());

        String after = remote.execute();
        String expectedAfter = "RADIO | power=ON | volume=30";

        boolean sameObject = originalReference == remote;

        boolean stateUnchanged =
                originalId.equals(remote.getId())
                        && originalVolumePreset == remote.getVolumePreset();

        boolean resultsCorrect =
                expectedBefore.equals(before)
                        && expectedAfter.equals(after);

        boolean passed =
                sameObject
                        && stateUnchanged
                        && resultsCorrect;

        System.out.println(
                "T5 " + (passed ? "PASS" : "FAIL")
                        + " | BasicRemote: TvDevice -> RadioDevice"
                        + " | sameObject=" + sameObject
                        + " | stateUnchanged=" + stateUnchanged
        );

        System.out.println(
                "   before=" + before
                        + " | after=" + after
        );

        if (!passed) {
            System.out.println(
                    "   expectedBefore=" + expectedBefore
                            + " | expectedAfter=" + expectedAfter
            );
        }

        return passed;
    }

    private static boolean runT6() {
        BasicRemote remote =
                new BasicRemote("BASIC-02", new ProjectorDevice());

        String actual = remote.execute();
        String expected = "PROJECTOR | power=ON | volume=30";

        return printResult(
                "T6",
                "BasicRemote + ProjectorDevice",
                actual,
                expected
        );
    }

    private static boolean runT7() {
        QuietRemote remote =
                new QuietRemote("QUIET-02", new ProjectorDevice());

        String actual = remote.execute();
        String expected = "PROJECTOR | power=ON | volume=5";

        return printResult(
                "T7",
                "QuietRemote + ProjectorDevice",
                actual,
                expected
        );
    }

    private static boolean printResult(
            String testId,
            String participants,
            String actual,
            String expected
    ) {
        boolean passed = expected.equals(actual);

        System.out.println(
                testId
                        + " " + (passed ? "PASS" : "FAIL")
                        + " | " + participants
                        + " | result=" + actual
        );

        if (!passed) {
            System.out.println(
                    "   expected=" + expected
            );
        }

        return passed;
    }
}