package com.example.administrator.myvoice;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3;
    ScrollView scrollView;
    TextView textView;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 오디오 권한 설정
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 5);
            toast("권한 허용 없이는 음성 인식 기능 사용 불가 ");
        }
        // 객체 생성
        setView();
    }

    // 객체 생성
    public void setView(){
        textView = (TextView) findViewById(R.id.textView);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);

        // TTS 언어 : 한국어
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.KOREAN);
            }
        });
    }

    // 음성인식관련 메서드
    public void inputVoice(final TextView txt) {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
            final SpeechRecognizer stt = SpeechRecognizer.createSpeechRecognizer(this);
            stt.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    toast("음성 입력 시작...");
                }

                @Override
                public void onBeginningOfSpeech() {
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                }

                @Override
                public void onEndOfSpeech() {
                    toast("음성 입력 종료");
                }

                @Override
                public void onError(int error) {
                    toast("오류 발생 : " + error);
                    stt.destroy();
                }

                @Override
                public void onResults(Bundle results) {
                    final ArrayList<String> result = (ArrayList<String>) results.get(SpeechRecognizer.RESULTS_RECOGNITION);
                    txt.append("[나] "+result.get(0)+"\n");
                    // 1초 딜레이
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            replyAnswer(result.get(0), txt);
                        }
//                    }, 1000);
                    }, 10000);
                    stt.destroy();
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
            stt.startListening(intent);
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    // 답에 대한 메서드
    private void replyAnswer(String input, TextView txt){
        try{
            if(input.contains("안녕")){
                txt.append("[니들요정] 누구세요?\n");
                tts.speak("누구세요?", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.contains("응")){
                txt.append("[니들요정] 오 대박?\n");
                tts.speak("오 대박", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.contains("아니오")){
                txt.append("[니들요정] 아니오.. 유감..\n");
                tts.speak("아니오.. 유감..", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(input.equals("꺼져")){
                tts.speak("응수고", TextToSpeech.QUEUE_FLUSH, null);
                // 1초 뒤에 꺼진다
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
            else {
                txt.append("[니들요정]" + input + "?? 뭐라는거야?\n");
                tts.speak(input, TextToSpeech.QUEUE_FLUSH, null);
//                txt.append("[니들요정] 뭐라는거야?\n");
//                tts.speak("뭐라는거야?", TextToSpeech.QUEUE_FLUSH, null);
            }
        } catch (Exception e) {
            toast(e.toString());
        }
    }

    // 토스트용
    private void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // 버튼 클릭 이벤트
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                // 음성인식 시작
                inputVoice(textView);
                break;
            case R.id.btn2:
                // 만약 입력된 값이 휴머로그라면?
                tts.speak("휴머로그가 맞습니까?", TextToSpeech.QUEUE_FLUSH, null);
                break;
            case R.id.btn3:
                // 스피너페이지로 이동
                Intent intent = new Intent(MainActivity.this, SpinnerActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                // 인슐린 종합 데이터
                String result = data.getStringExtra("AA");
                Toast.makeText(getApplicationContext(), "result = "+ result, Toast.LENGTH_SHORT).show();
                replyAnswer(result, textView);
            }
        }
    }
}
