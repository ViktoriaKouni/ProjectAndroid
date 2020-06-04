package com.example.sep4android.UI.Views.Fragments;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.sep4android.DATA.Models.ArchiveRoom;
import com.example.sep4android.R;
import com.example.sep4android.UI.ViewModels.ConditionsViewModel;
import com.example.sep4android.UI.Views.Activities.ConditionActivity;

public class HomeFragment extends Fragment {
    private TextView CO2Value;
    private TextView TemperatureValue;
    private TextView HumidityValue;
    private ArchiveRoom archiveRoom;

    public HomeFragment(final ConditionActivity conditionActivity, ConditionsViewModel conditionsViewModel) {
        conditionsViewModel.getArchiveRoomLatestValue().observe(this, new Observer<ArchiveRoom>() {
            @Override
            public void onChanged(ArchiveRoom room) {
                if (!room.equals(archiveRoom)) {
                    archiveRoom = room;
                    String co2 = String.format("%.2f", archiveRoom.getCO2().getValue()) + "%";
                    String temperature = String.format("%.2f", archiveRoom.getTemperature().getValue()) + "°C";
                    String humidity = String.format("%.2f", archiveRoom.getHumidity().getValue()) + "%";

                    //On click on the current value, a toast appears with the recommended one
                    SpannableString spannableStringCO2 = new SpannableString(co2);
                    ClickableSpan clickableSpanCO2 = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Toast.makeText(conditionActivity, "The recommended value for CO2 is " + archiveRoom.getOptimalValues().getOptimalCO2() + "%", Toast.LENGTH_LONG).show();
                        }
                    };
                    spannableStringCO2.setSpan(clickableSpanCO2, 0, spannableStringCO2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    CO2Value.setText(spannableStringCO2);
                    CO2Value.setMovementMethod(LinkMovementMethod.getInstance());


                    SpannableString spannableStringTemperature = new SpannableString(temperature);
                    ClickableSpan clickableSpanTemperature = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Toast.makeText(conditionActivity, "The recommended value for Temperature is " + archiveRoom.getOptimalValues().getOptimalTemperature() + "°C", Toast.LENGTH_LONG).show();
                        }
                    };
                    spannableStringTemperature.setSpan(clickableSpanTemperature, 0, spannableStringTemperature.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    TemperatureValue.setText(spannableStringTemperature);
                    TemperatureValue.setMovementMethod(LinkMovementMethod.getInstance());


                    SpannableString spannableStringHumidity = new SpannableString(humidity);
                    ClickableSpan clickableSpanHumidity = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            Toast.makeText(conditionActivity, "The recommended value for Humidity is " + archiveRoom.getOptimalValues().getOptimalHumidity() + "%", Toast.LENGTH_LONG).show();
                        }
                    };
                    spannableStringHumidity.setSpan(clickableSpanHumidity, 0, spannableStringHumidity.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    HumidityValue.setText(spannableStringHumidity);
                    HumidityValue.setMovementMethod(LinkMovementMethod.getInstance());

                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        CO2Value = rootView.findViewById(R.id.co2Value);
        TemperatureValue = rootView.findViewById(R.id.temperatureValue);
        HumidityValue = rootView.findViewById(R.id.humidityValue);
        return rootView;
    }
}
