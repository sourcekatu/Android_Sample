package sample.sample_socket_client_repeat;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

// AsyncTaskの引数は以下の用途で使用。
// 第一引数：doInBackgroundの引数
// 第二引数：onProgressUpdateの引数
// 第三引数：doInBackgroundの戻り値 and onPostExecuteの引数
public class ASyncTaskTest extends AsyncTask<InetSocketAddress, Void, String> {

    private CallBackTask mcallbacktask;
    private byte mbuf[];

    public ASyncTaskTest(byte buf[])
    {
        mbuf = buf;
    }

    // バックグラウンドの処理。ここではTextViewの操作はできない。
    @Override
    protected String doInBackground(InetSocketAddress... inetSocketAddresses) {
        Socket socket = null;
        try {
            // 接続
            socket = new Socket();
            socket.connect(inetSocketAddresses[0]);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // データを送信。
            writer.write(String.valueOf(mbuf));

            // クローズしておかないと、受信側でデータが反映されなかった。多分、クローズしたときに受信が側にデータが反映される。
            writer.close();

            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // doInBackgroundの処理が終了した後に呼ばれる。ここではTextViewの操作はできる。
    @Override
    protected void onPostExecute(String result) {
        mcallbacktask.CallBack();
    }

    public void setOnCallBack(CallBackTask t_object)
    {
        mcallbacktask = t_object;
    }

    // コールバック用のインターフェース定義
    interface CallBackTask
    {
        void CallBack();
    }
}
