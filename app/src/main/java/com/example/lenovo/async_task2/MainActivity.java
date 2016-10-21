package com.example.lenovo.async_task2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity{
Button b1;
    ProgressBar P1,P2,P3,P4;
    private TextView finalResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finalResult = (TextView) findViewById(R.id.result);


        b1=(Button)findViewById(R.id.start);
        P1=(ProgressBar) findViewById(R.id.P1);
        P2=(ProgressBar) findViewById(R.id.P2);
        P3=(ProgressBar) findViewById(R.id.P3);
        P4=(ProgressBar) findViewById(R.id.P4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download runner = new Download(P1);

                runner.execute();
                Download runner3 = new Download(P3);

                runner3.execute();
                Download runner2 = new Download(P2);

                StartAsyncTaskInParallel(runner2);

                Download runner4 = new Download(P4);

                runner4.execute();


            }
        });
    }
    private class Download extends AsyncTask <Void,Integer,String>{
        ProgressBar myProgressBar;
        private String resp;
        public Download(ProgressBar target) {
            myProgressBar = target;
        }

        @Override
        protected String doInBackground(Void... params) {
            for(int i=0; i<100; i++){
                publishProgress(i);
                // Sleeping for given time period
                SystemClock.sleep(100);
                resp = "Download complete";
            }


            return resp;

        }

        @Override
        protected void onProgressUpdate(Integer... text) {
            myProgressBar.setProgress(text[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            finalResult.setText(result);
        }


    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void StartAsyncTaskInParallel(Download task) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else

            task.execute();
    }


}
