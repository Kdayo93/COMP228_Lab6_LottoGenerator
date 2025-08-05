import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoDrawThread implements Runnable {
    private final int min;
    private final int max;
    private final int quantity;
    private final int runNumber;
    private final ResultCallback callback;

    public interface ResultCallback {
        void onComplete(int runNumber, List<Integer> result);
    }

    public LottoDrawThread(int min, int max, int quantity, int runNumber, ResultCallback callback) {
        this.min = min;
        this.max = max;
        this.quantity = quantity;
        this.runNumber = runNumber;
        this.callback = callback;
    }

    @Override
    public void run() {
        SecureRandom random = new SecureRandom();
        Set<Integer> lottoNumbers = new HashSet<>();

        while (lottoNumbers.size() < quantity) {
            int number = random.nextInt((max - min) + 1) + min;
            lottoNumbers.add(number);
        }

        callback.onComplete(runNumber, new ArrayList<>(lottoNumbers));
    }
} 