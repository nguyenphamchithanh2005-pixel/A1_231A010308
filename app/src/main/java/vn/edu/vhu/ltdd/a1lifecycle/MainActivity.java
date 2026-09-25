package vn.edu.vhu.ltdd.a1lifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "A1_231A010308";

    private TextView tvLog;
    private TextView tvCounter; // NC1: hiển thị bảng đếm
    private final StringBuilder history = new StringBuilder();
    private int step = 0;

    // NC1: đếm số lần gọi mỗi callback, LinkedHashMap giữ đúng thứ tự thêm vào
    private final Map<String, Integer> callCount = new LinkedHashMap<>();

    /** Ghi một sự kiện ra Logcat và hiển thị lên màn hình. */
    private void logEvent(String event) {
        step++;
        String time = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
                .format(new Date());
        String line = step + ". [" + time + "] " + event;
        Log.d(TAG, line);
        history.append(line).append('\n');
        if (tvLog != null) {
            tvLog.setText(history);
        }

        // NC1: cập nhật số đếm cho callback này rồi vẽ lại bảng đếm
        callCount.merge(event, 1, Integer::sum);
        updateCounterView();
    }

    /** NC1: dựng chuỗi hiển thị dạng "onResume: 3 | onPause: 2 | ..." */
    private void updateCounterView() {
        if (tvCounter == null) return;
        StringBuilder sb = new StringBuilder("Số lần gọi: ");
        boolean first = true;
        for (Map.Entry<String, Integer> e : callCount.entrySet()) {
            if (!first) sb.append("  |  ");
            sb.append(e.getKey()).append(": ").append(e.getValue());
            first = false;
        }
        tvCounter.setText(sb.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvLog = findViewById(R.id.tvLog);
        tvCounter = findViewById(R.id.tvCounter); // NC1
        Button btnClear = findViewById(R.id.btnClear);
        Button btnCrash = findViewById(R.id.btnCrash);
        Button btnFinish = findViewById(R.id.btnFinish);

        btnClear.setOnClickListener(v -> {
            history.setLength(0);
            step = 0;
            tvLog.setText("");
            callCount.clear();       // NC1: xóa luôn bảng đếm
            updateCounterView();
            Log.i(TAG, "---- Đã xóa lịch sử ----");
        });

        // NC2: bắt lỗi NullPointerException thay vì để app crash
        btnCrash.setOnClickListener(v -> {
            try {
                String ten = null;
                Log.d(TAG, "Độ dài tên: " + ten.length());
            } catch (NullPointerException e) {
                Log.e(TAG, "Bắt được lỗi NullPointerException", e);
                Toast.makeText(this, "Đã bắt lỗi: tên đang null", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinish.setOnClickListener(v -> finish());

        String state = (savedInstanceState == null) ? "= null" : "!= null";
        logEvent("onCreate (savedInstanceState " + state + ")");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logEvent("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logEvent("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logEvent("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logEvent("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logEvent("onRestart");
    }

    @Override
    protected void onDestroy() {
        logEvent("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logEvent("onSaveInstanceState");
    }
}