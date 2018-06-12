package sample.sample_socket_client_repeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity implements ASyncTaskTest.CallBackTask, View.OnClickListener {
    TextView tw;
    ASyncTaskTest ak;
    Button button;
    Integer ii = 0;
    Integer count = 0;

    Spinner spinner1;   // IPアドレス1
    Spinner spinner2;   // IPアドレス2
    Spinner spinner3;   // IPアドレス3
    Spinner spinner4;   // IPアドレス4
    EditText et;         // 送信テキスト
    InetSocketAddress inetSocketAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);

        // IPアドレス設定 0 ～ 255 Start-----------------------------------
        String items[] = new String[256];
        int ii;

        for (ii = 0; ii < 256; ii++)
        {
            items[ii] = String.valueOf(ii);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        // IPアドレス設定 0 ～ 255 End------------------------------------

        // IPアドレスの初期値設定 192.168.0.0
        spinner1.setSelection(192);
        spinner2.setSelection(168);
        spinner3.setSelection(0);
        spinner4.setSelection(0);

        Button button = (Button) findViewById(R.id.button);

        // 送信ボタン押下処理
        button.setOnClickListener(this);
    }

    // 非同期タスクのコールバックが呼ばれるたびに処理を行う。
    @Override
    public void CallBack() {
        count++;

        // 再度非同期タスク設定して実行。
        ak = new ASyncTaskTest(count);
        ak.setOnCallBack(this);
        ak.execute(inetSocketAddress);  // 非同期タスクを実行
    }

    @Override
    public void onClick(View v) {
        String IPAddress = String.valueOf(spinner1.getSelectedItem()) + '.' +
                           String.valueOf(spinner2.getSelectedItem()) + '.' +
                           String.valueOf(spinner3.getSelectedItem()) + '.' +
                           String.valueOf(spinner4.getSelectedItem());

        byte buf[] = new byte[String.valueOf(count).length()];

        // データの送信はbyte配列型になるので変換
        buf = String.valueOf(count).getBytes();

        // コールバック設定
        ak = new ASyncTaskTest(buf);
        ak.setOnCallBack(this);

        // ソケット通信用にポート設定。送信したいデータとIPアドレス設定。
        inetSocketAddress = new InetSocketAddress(IPAddress, 5000);
        ak.execute(inetSocketAddress);  // 非同期タスクを実行
    }
}
