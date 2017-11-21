package samzhu.stripe_sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

public class MainActivity extends AppCompatActivity {
    CardInputWidget mCardInputWidget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCardInputWidget = (CardInputWidget)findViewById(R.id.card_input_widget);

        Button btnOk = (Button)findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"TEST",Toast.LENGTH_SHORT).show();
                createToken();
            }
        });

        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToken();
            }
        });
    }

    public void createToken(){


        Stripe stripe = new Stripe(MainActivity.this, "pk_test_qVghHOVxwENIZb25axn8SVR9");
        stripe.createToken(
                new Card("4242424242424242", 12, 2018, "123"),
                new TokenCallback() {
                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(MainActivity.this,
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Token token) {
                        Toast.makeText(MainActivity.this,
                                token.getId(),
                                Toast.LENGTH_LONG).show();
                        Log.println(Log.ASSERT, "TAG", token.getType());
                        Log.println(Log.ASSERT, "TAG", token.getCreated().toString());
                        Log.println(Log.ASSERT, "TAG", String.valueOf(token.getUsed()));
                        Log.println(Log.ASSERT, "TAG", String.valueOf(token.getLivemode()));

                    }
                }
        );

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {
            return;
        }
    }
}
