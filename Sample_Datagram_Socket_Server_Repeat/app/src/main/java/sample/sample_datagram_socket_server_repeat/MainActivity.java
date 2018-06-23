package sample.sample_datagram_socket_server_repeat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ASyncTaskTest ak;
    EditText et;
    DatagramSocket datagramSocket = null;
    DatagramPacket datagramPacket;
    byte receiveBuff[] = new byte[1000];
    String string = "NG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        et = (EditText)findViewById(R.id.editText);

        // 送信ボタン押下処理
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        // コールバック設定
        ak = new ASyncTaskTest(et);

        // 非同期呼び出し。直列実行。並列実行。
        ak.execute();  // 非同期タスクを実行
        //ak.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
