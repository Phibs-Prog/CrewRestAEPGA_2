package com.phibs.crewrestsipla;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.view.View;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;


public class CrewRest extends AppCompatActivity {

    SharedPreferences prefs ;
    ImageView imageNocturno, imageCircadiano, imageViewHelpMinimumRest, imageViewHelpSplit, imageViewHelpQuintoDia;
    TextView calcosVal, dutyVal, dutyMaxVal, dutyMaxValAE, textMaxDutyAE, dutyMaxBlockSplit, dutyMaxSplit, dutyMaxBlock, dutyMaxBlockAE, textExtraCrew;
    TextView calcosOut, calcosIn, calcosOutSplit, calcosInSplit, extraCrew, splitDutyMaxTextAE;// nextReport;
    TextView textIO, textRefTimeCo, textDutyFTL, textRefTimeNextOfBlock, textNocturno, textCircadiano, textDutyWOCL;
    Button calcDescanso, calcDutyMax, calcDescansoSplit;
    Switch  switchBaseIn, switchExtraCrew, switchDhcBase;
    Spinner transp, sectors,transpSplit;
    double horasI, horasISplit, horasO, horasOSplit, minutosO, minutosOSplit, minutosI, minutosISplit, dutyStart, dutyFinish;
    double dutyFinDHC, calcosO, calcosI, calcosISplit, calcosOSplit, duty, dutyMin, descanso, saida, saidaMin;// saidaNextMin;
    double psvMax, psvMaxAE, psvMaxMin, psvMaxMinAE, descontoPSV, horasDhc, minutosDhc, calcosDhc, dutyMaxCalcos, dutyMaxCalcosAE, dutyMaxCalcosAE9;
    double dutyMaxCalcosM, dutyMaxCalcosMAE, splitRest, splitDutyMax, splitDutyBlock; //saidaNextReport;
    Integer dutyHora, saidaHora, psvMaxHora, psvMaxHoraAE, nsectores, dutyMaxCalcosH, dutyMaxCalcosHAE, ttransporte, ttransporteSplit, circadiano;// saidaNextHora;
    String transporte, sectores, transporteSplit;
    boolean isShowAgain=true;
    boolean accepted=false;
    CheckBox dialogcb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_rest);
        calcosOut = (TextView) findViewById(R.id.calcosOut);
        calcosIn = (TextView) findViewById(R.id.calcosIn);
        calcosInSplit = (TextView) findViewById(R.id.calcosInSplit);
        calcosOutSplit = (TextView) findViewById(R.id.calcosOutSplit);
        extraCrew = (TextView) findViewById(R.id.extraCrew);
        calcosVal = (TextView) findViewById(R.id.calcosVal);
        dutyVal = (TextView) findViewById(R.id.dutyVal);
        dutyMaxVal = (TextView) findViewById(R.id.dutyMaxVal);
        dutyMaxValAE = (TextView) findViewById(R.id.dutyMaxValAE);
        dutyMaxBlock = (TextView) findViewById(R.id.dutyMaxBlock);
        dutyMaxBlockAE = (TextView) findViewById(R.id.dutyMaxBlockAE);
        textMaxDutyAE = (TextView) findViewById(R.id.textMaxDutyAE);
        dutyMaxBlockSplit = (TextView) findViewById(R.id.dutyMaxBlockSplit);
        dutyMaxSplit = (TextView) findViewById(R.id.dutyMaxSplit);
        splitDutyMaxTextAE = (TextView) findViewById(R.id.splitDutyMaxTextAE);
        calcDescanso = (Button) findViewById(R.id.calcDescanso);
        calcDutyMax = (Button) findViewById(R.id.calcDutyMax);
        calcDescansoSplit = (Button) findViewById(R.id.calcDescansoSplit);
        switchBaseIn = (Switch) findViewById(R.id.switchBaseIn);
        switchExtraCrew = (Switch) findViewById(R.id.switchExtraCrew);
        switchDhcBase = (Switch) findViewById(R.id.switchDhcBase);
        /*switchQuintoDia = (Switch) findViewById(R.id.switchQuintoDia);*/
        transp = (Spinner) findViewById(R.id.transp);
        sectors = (Spinner) findViewById(R.id.sectors);
        transpSplit = (Spinner) findViewById(R.id.transpSplit);
        textExtraCrew = (TextView) findViewById(R.id.textExtraCrew);
        imageNocturno = (ImageView) findViewById(R.id.imageNocturno);
        imageCircadiano = (ImageView) findViewById(R.id.imageCircadiano);

        imageViewHelpMinimumRest = (ImageView) findViewById(R.id.imageViewHelpMinimumRest);
        imageViewHelpSplit = (ImageView) findViewById(R.id.imageViewHelpSplit);

        textIO = (TextView) findViewById(R.id.textIO);
        textRefTimeCo = (TextView) findViewById(R.id.textRefTimeCo);
        textDutyFTL = (TextView) findViewById(R.id.textDutyFTL);
        textRefTimeNextOfBlock = (TextView) findViewById(R.id.textRefTimeNextOfBlock);
        textNocturno = (TextView) findViewById(R.id.textNocturno);
        textCircadiano = (TextView) findViewById(R.id.textCircadiano);
        textDutyWOCL = (TextView) findViewById(R.id.textDutyWOCL);

        calcosO = 0;
        calcosI = 0;
        calcosOSplit = 0;
        calcosISplit = 0;

        // Spinner Transporte
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tempTransp, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        transp.setAdapter(adapter);
        transp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transporte = parent.getItemAtPosition(position).toString();
                transporte= transporte.substring(0, 2);
                ttransporte = Integer.parseInt(transporte);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CrewRest.this,
                        "Tem de seleccionar o tempo de transporte", Toast.LENGTH_LONG).show();
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.numbOfSectors, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sectors.setAdapter(adapter2);
        sectors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sectores = parent.getItemAtPosition(position).toString();
                sectores = sectores.substring(0, 1);
                nsectores = Integer.parseInt(sectores);
                if (nsectores == 1) {
                    nsectores = 0;
                } else {
                    nsectores = nsectores - 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CrewRest.this,
                        "Tem de seleccionar o número de sectores", Toast.LENGTH_LONG).show();
            }
        });

        // Spinner TransporteSplit
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.tempTranspSplit, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        transpSplit.setAdapter(adapter3);
        transpSplit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transporteSplit = parent.getItemAtPosition(position).toString();
                transporteSplit= transporteSplit.substring(0, 2);
                ttransporteSplit = Integer.parseInt(transporteSplit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(CrewRest.this,
                        "Tem de seleccionar o tempo de transporte", Toast.LENGTH_LONG).show();
            }
        });


        // Mostrar Disclaimer
        prefs = getSharedPreferences("detail", MODE_PRIVATE);
        boolean isshowagain =prefs.getBoolean("show_again", true);
        if(isshowagain)
            showdialog();

        calcosOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CrewRest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calcosOut.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                        horasO = selectedHour;
                        minutosO = selectedMinute;
                        calcosO = horasO + (minutosO / 60);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Off Blocks");
                mTimePicker.show();
            }

        });

        calcosIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CrewRest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calcosIn.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)); //String format usado para ter dois digitos
                        horasI = selectedHour;
                        minutosI = selectedMinute;
                        calcosI = horasI + (minutosI / 60);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("On Blocks");
                mTimePicker.show();

            }
        });

        extraCrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CrewRest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        extraCrew.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)); //String format usado para ter dois digitos
                        horasDhc = selectedHour;
                        minutosDhc = selectedMinute;
                        calcosDhc = horasDhc + (minutosDhc / 60);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Hora Dhc");
                mTimePicker.show();
            }
        });

        calcosInSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CrewRest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calcosInSplit.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)); //String format usado para ter dois digitos
                        horasISplit = selectedHour;
                        minutosISplit = selectedMinute;
                        calcosISplit = horasISplit + (minutosISplit / 60);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("On Blocks Split");
                mTimePicker.show();

            }
        });

        calcosOutSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CrewRest.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calcosOutSplit.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute)); //String format usado para ter dois digitos
                        horasOSplit = selectedHour;
                        minutosOSplit = selectedMinute;
                        calcosOSplit = horasOSplit + (minutosOSplit / 60);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Off Blocks Split");
                mTimePicker.show();

            }
        });

        switchExtraCrew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchExtraCrew.isChecked()) {
                    extraCrew.setVisibility(View.VISIBLE);
                    textExtraCrew.setVisibility(View.VISIBLE);
                    switchDhcBase.setVisibility(View.VISIBLE);
                } else {
                    extraCrew.setVisibility(View.INVISIBLE);
                    textExtraCrew.setVisibility(View.INVISIBLE);
                    switchDhcBase.setVisibility(View.INVISIBLE);
                }
            }
        });

        calcDescanso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circadiano = 0;
                dutyStart = Math.round((calcosO - 1) * 100);
                dutyStart = dutyStart / 100;
                dutyFinish = Math.round((calcosI + 0.5) * 100);
                dutyFinish = dutyFinish / 100;
                if (switchExtraCrew.isChecked()) {
                    dutyFinDHC = Math.round((calcosDhc + 0.5)* 100);
                    dutyFinDHC = dutyFinDHC / 100;
                }
                // Nocturno
                if ((dutyStart<6.5)||(dutyStart>=23)) {
                    imageNocturno.setBackgroundResource(android.R.drawable.checkbox_on_background);
                }else{
                    if ((dutyFinish<6.5)||(dutyFinish>=23)||(dutyFinish<dutyStart)){
                        imageNocturno.setBackgroundResource(android.R.drawable.checkbox_on_background);
                    }else {
                        if (switchExtraCrew.isChecked()) {
                            if ((dutyFinDHC < 6.5) || (dutyFinDHC >= 23)||(dutyFinDHC<dutyFinish)) {
                                imageNocturno.setBackgroundResource(android.R.drawable.checkbox_on_background);
                            } else {
                                imageNocturno.setBackgroundResource(android.R.drawable.checkbox_off_background);
                            }
                        }else{
                            imageNocturno.setBackgroundResource(android.R.drawable.checkbox_off_background);
                        }
                    }
                }
                //Circadiano
                if ((dutyStart < 6)&&(dutyStart>=2)) { //Por novo DL 5:59 inclusivé Alteração Março 23.
                    imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_on_background);
                    textDutyWOCL.setText(" 2 horas extra de descanso AE Cl. 35º");
                    textDutyWOCL.setVisibility(View.VISIBLE);
                    circadiano = 2; //2 horas extra de descanso
                }else{
                    if ((dutyFinish < 6)&&(dutyFinish>=2)){
                        imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_on_background);
                        textDutyWOCL.setText(" 2 horas extra de descanso AE Cl. 35º ");
                        textDutyWOCL.setVisibility(View.VISIBLE);
                        circadiano = 2;
                    }else {
                        if ((dutyFinish>= 6)&&(dutyFinish<dutyStart)){ // Cancelado (Por DL 6:00 inclusivé > em vez de >=)
                            imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_on_background);
                            textDutyWOCL.setText(" 2 horas extra de descanso AE Cl. 35º ");
                            textDutyWOCL.setVisibility(View.VISIBLE);
                            circadiano = 2;
                        }else {
                            if (switchExtraCrew.isChecked()) {
                                if ((dutyFinDHC < 6) && (dutyFinDHC >= 2)) {
                                    imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_on_background);
                                    textDutyWOCL.setText(" 2 horas extra de descanso AE Cl. 35º ");
                                    textDutyWOCL.setVisibility(View.VISIBLE);
                                    circadiano = 2;
                                } else {
                                    if ((dutyFinDHC >= 6) && (dutyFinDHC < dutyFinish )) {
                                        imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_on_background);
                                        textDutyWOCL.setText(" 2 horas extra de descanso AE Cl. 35º ");
                                        textDutyWOCL.setVisibility(View.VISIBLE);
                                        circadiano = 2;
                                    } else {
                                        imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_off_background);
                                        circadiano = 0;
                                        textDutyWOCL.setVisibility(View.INVISIBLE);
                                    }
                                }
                            } else {
                                imageCircadiano.setBackgroundResource(android.R.drawable.checkbox_off_background);
                                circadiano = 0;
                                textDutyWOCL.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }
                double temptransp = (ttransporte * 2);
                temptransp = temptransp / 60;
                if (switchExtraCrew.isChecked()) {
                    if (calcosO > calcosI) {
                        calcosI = calcosI + 24;
                        calcosDhc = calcosDhc + 24;
                    } else {
                        if (calcosDhc < calcosI) {
                            calcosDhc = calcosDhc + 24;
                        }
                    }
                    duty = (calcosDhc - calcosI) / 2 + (calcosI - (calcosO - 1));
                    if (switchDhcBase.isChecked()) {
                        if (duty  > 12) {
                            /*descanso = (duty * 1.25) + 4.5; // 4.5 = 3 + 1 briefing + 30 debriefing*/
                            descanso = duty + 4;
                        } else {
                            descanso = 16; // Passou para 16:00
                        }
                    } else {
                        if (duty > 10) { // Passou de 9 para 10 por causa das 13 horas (12 livres de serviço)
                            descanso = duty + 3 + temptransp;
                        } else {
                            descanso = 13 + temptransp; // Passou de 12 para 13
                        }
                    }
                    if (calcosDhc > 24) {
                        calcosDhc = calcosDhc - 24;
                    }
                    /*if ((switchQuintoDia.isChecked())&&(switchDhcBase.isChecked())){
                        if(calcosDhc + descanso >= 24){
                            descanso = descanso + 2;
                        }
                    }*/
                    saida = calcosDhc + descanso + circadiano;
                } else {
                    if (calcosO > calcosI) {
                        calcosI = calcosI + 24;
                    }
                    duty = calcosI - (calcosO - 1);
                    if (switchBaseIn.isChecked()) {
                        if (duty  > 12) {
                            descanso = duty  + 4;
                        } else {
                            descanso = 16;
                        }
                    } else {
                        if (duty > 10) {
                            descanso = duty + 3 + temptransp;
                        } else {
                            descanso = 13 + temptransp;
                        }
                    }
                    if (calcosI > 24) {
                        calcosI = calcosI - 24;
                    }
                    /*if ((switchQuintoDia.isChecked())&&(switchBaseIn.isChecked())){
                        if(calcosI + descanso >= 24){
                            descanso = descanso + 2;
                        }
                    }*/
                    saida = calcosI + descanso + circadiano;
                }

                if (saida > 24) {
                    saidaHora = (int) saida - 24;
                } else {
                    saidaHora = (int) saida;
                }
                saidaMin = Math.round((saida - (int) saida) * 60);
                calcosVal.setText(String.format("%02d", saidaHora) + ":" + String.format("%02d", (int) saidaMin));
                calcosVal.setVisibility(View.VISIBLE);
                dutyHora = (int) duty;
                dutyMin = Math.round((duty - (int) duty) * 60); // Math.ceil usado para arredondar
                if (dutyMin == 60) {    // Por causa dos arredondamentos em DHC
                    dutyMin = 0;        // Retornava 60 minutos
                    dutyHora = dutyHora + 1;
                }
                dutyVal.setText(String.format("%02d", dutyHora) + ":" + String.format("%02d", (int) dutyMin));
                dutyVal.setVisibility(View.VISIBLE);
            }
        });

        imageViewHelpMinimumRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Tempo de repouso nas frotas Entry Narrow-Body");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Claúsula 35.º" + "<br>" +"</b>"+
                        "1. O tempo de repouso tem a duração igual ao período de trabalho planeado,\n" +
                        "desde que superior ao tempo mínimo de repouso, o qual é:\n" + "<br>"+ "<b>"+
                        "a) Na base"+"</b>"+", antes de "+"<b>"+"trabalho em voo – 12 horas;\n" +"<br>"+
                        "b) Na base,"+"</b>"+" antes de "+"<b>"+"trabalho no solo, após trabalho em voo – 9 horas;\n" +"<br>"+
                        "e) Fora da base – 10 horas, sem prejuízo do disposto na Cláusula 37.ª.\n" +"<br>"+"</b>"+
                        "3. Na base"+"</b>"+", em caso de irregularidade operacional "+"<b>"+"e apenas mediante acordo\n" +
                        "do Piloto, "+"</b>"+"poderá ser feita a redução do tempo de repouso, sem prejuízo das\n" +
                        "disposições legais aplicáveis, "+"<b>"+"até um máximo de 2 horas"+"</b>"+", aplicando-se o\n" +
                        "disposto no "+"<b>"+"número 8 da Cláusula 2.ª do RRRGS."+"<br>"+"<br>"+
                        "Cláusula 37.ª\n" +"<br>"+
                        "Voos com night-stop"+"</b>"+"<br>"+
                        "1. Nos serviços de "+"<b>"+"voo/rotações com night-stop"+"</b>"+", ...,\n" +
                        "aplicam-se as regras do "+"<b>"+"repouso mínimo fora da base"+"</b>"+"; no entanto, "+"<b>"+"quando\n" +
                        "não existir disponibilidade de tripulações"+"</b>"+", o período de "+"<b>"+"repouso mínimo pode\n" +
                        "ser inferior a 11 horas"+"</b>"+", ... só podendo, nesta situação, os Pilotos efetuar uma combinação de "+"<b>"+"até quatro\n" +
                        "sectores antes e após"+"</b>"+" o night-stop."+"<br>"+
                        "2. Às reduções previstas no número anterior, aplica-se o disposto no número 8\n" +
                        "da Cláusula 2.ª do RRRGS.\n" +"<br>"+
                        "3. O regime previsto no número anterior "+"<b>"+"deve ser utilizado com carácter\n" +
                        "excecional"+"</b>"+", devendo a comunicação com os Pilotos "+"<b>"+"ser feita pela DOV."+"</b>"));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        textNocturno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Acordo de Empresa SIPLA");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Claúsula 23.º" + "<br>" +
                        "Limites ao Early Start, Late Finish e Night Duty" + "<br>" +
                        "1. No seu planeamento mensal," + "</b>" + " o Piloto pode ver atribuído" + "<b>" + " entre períodos de folga:"
                        + "<br>" +  "a) dois períodos"+"</b>"+ " de serviço de voo "+"<b>"+"consecutivos"+"</b>"+" caraterizados como Early\n" +
                        "Start, Late Finish ou Night Duty, combinados entre si ou do mesmo tipo;" + "<br>" +"<b>"+ "b) três períodos"+"</b>"+" de serviço caracterizados como Early Start, Late Finish ou\n" +
                        "Night Duty, combinados entre si ou do mesmo tipo;" + "<br>" + "<b>"+"c) três períodos"+"</b>"+" de serviço de voo"+"<b>"+" consecutivos"+"</b>"+", se caracterizados\n"
                        + "<b>" + "exclusivamente como Early Start" + "</b>" + ", desde que sejam "+"<b>"+ "imediatamente\n" +
                        "precedidos e sucedidos"+"</b>"+" por períodos de folga. "+ "<br>"+"2. Em Operação e "+"<b>"+"apenas por irregularidade, ocorrida nas duas horas que\n" +
                        "antecedem a apresentação ou após esta:"+"</b>" + "<br>"+
                        "a) Como exceção ao número 1, alínea a), se um Piloto operar o terceiro\n" +
                        "período de serviço consecutivo caracterizado como Early Start, Late Finish\n" +
                        "ou Night Duty, "+"<b>"+"não poderá operar mais nenhum"+"</b>"+" desses tipos de período de\n" +
                        "serviço "+"<b>"+"entre períodos de folga."+"</b>" + "<br>" +
                        "b) Como exceção ao número 1, alínea b), se um Piloto operar "+"<b>"+"mais de três\n" + "</b>"+
                        "períodos de serviço "+"<b>"+"consecutivos"+"</b>"+" caracterizados como Early Start, Late\n" +
                        "Finish ou Night Duty "+"<b>"+"entre períodos de folga, o segundo"+"</b>"+" desses períodos\n" +
                        "de folga tem uma "+"<b>"+"duração mínima de 72 horas"+"</b>"+"; neste contexto excecional\n" +
                        "é permitido que o Piloto realize no "+"<b>"+"máximo quatro"+"</b>"+" desses períodos de\n" +
                        "serviços entre os períodos de folga.\n"+
                        "<b>"+"<br>"+"3. Na transição entre períodos"+"</b>"+" de serviço de voo Late Finish para Early Start,\n" +
                        "<b>" + "planeada na base, é obrigatória"+"</b>"+" a inclusão de um período noturno de repouso\n" +
                        "no intervalo"+"<b>"+" entre os dois serviços.\n"+"</b>" + "<br>"+
                        "4. Apenas em situações de irregularidade e para possibilitar o regresso à base,\n" +
                        "ou períodos de simulador e outros períodos de formação e de trabalho no\n" +
                        "solo, é permitida a realização do segundo período crítico consecutivo.\n" + "<br>"+
                        "5. Os limites previstos nos números 1, 2 e 4 não se aplicam à marcação de\n" +
                        "assistências na residência do Piloto.\n" + "<br>"+
                        "6. Para os efeitos dos números anteriores, ao trabalho 'entre períodos de folga'\n" +
                        "deverá ser observado o princípio da elaboração de escalas que permita um\n" +
                        "adiantamento gradual do horário de apresentação do Piloto, com o propósito\n" +
                        "específico de mitigar os efeitos de fadiga decorrentes de apresentações\n" +
                        "consecutivas nesse intervalo de tempo.\n" + "<br>"+
                        "7. Exclusivamente para os propósitos estabelecidos nesta Cláusula, define-se\n" +
                        "como "+"<b>"+"Early Start"+"</b>"+" o período que se estende das "+"<b>"+"05h00 às 06h29 do local"+"</b>"+" onde\n" +
                        "o Piloto se encontra aclimatizado."));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        textCircadiano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Acordo de Empresa SIPLA");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Claúsula 35.º" + "<br>" +
                        "Tempo de repouso nas frotas Entry Narrow-Body" + "</b>" + "<br>"+
                        "2. Sempre que um serviço de voo ou de simulador"+"<b>"+" esteja compreendido, no todo\n"+
                        "ou em parte"+"</b>"+", entre as 2 horas e as 5 horas e 59 minutos, "+"<b>"+"local de Lisboa"+"</b>"+", o\n"+
                        "tempo de repouso subsequente deve ser "+"<b>"+"aumentado em duas horas."));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        textIO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Acordo de Empresa SIPLA");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Claúsula 7.º" + "</b>" + "<br>" +"<br>"+
                        "18. Irregularidades operacionais – alterações decorrentes de dificuldades\n"+
                        "técnicas ou operacionais, não previsíveis e não remediáveis em tempo útil;\n"+ "<b>"+
                        "excluem-se as alterações ditadas por razões comerciais."+"</b>"));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });
        
        /*imageViewHelpQuintoDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this).create();
                alertDialog.setTitle("Acordo de Empresa SIPLA");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Cláusula 18.ª" + "<br>" +
                        "Noção e Regimes, Geral, Especiais e carácter Reparador das Folgas" + "</b>" + "<br>"+
                        "<br>"+"2. Caso a folga semanal"+"<b>"+" se inicie"+"</b>"+" entre as 00:00 horas e as 23:59 horas do "+"<b>"+"sexto dia de\n" +
                        "trabalho consecutivo"+"</b>"+", a duração da folga semanal será "+"<b>"+"acrescida de duas horas.\n"+"</b>" + "<br>"+
                        "3. O início da folga é contado "+"<b>"+"a partir do termo"+"</b>"+" do período de repouso do serviço de voo que\n" +
                        "o anteceda."
                ));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });*/

        textRefTimeCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Reference Time");
                alertDialog.setMessage(Html.fromHtml("<b>"+"Reference Time ORO.FTL.105"+"</b>"+"<br>"
                        + "<b>"+"(a) Reference Time"+"</b>"+" refers to reporting points in a"+"<b>"+" 2-hour wide time zone band" + "</b>"
                        +" around the local time where a crew member is acclimatised." + "<br>" +
                        "<b>" + "Acclimatised" +  "</b>" + "<br>" +
                        "(a) A crew member remains acclimatised to the local time of his or her reference time during " +
                        "<b>" + "47 hours 59 minutes" + "</b>" + " after reporting no matter how many time zones he/she has crossed."));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        /* MAXIMUM DUTY */

        calcDutyMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CrewRest.this,"A hora de Calços é a hora de origem LT", Toast.LENGTH_LONG).show();
//                imageExtension.setBackgroundResource(android.R.drawable.checkbox_off_background);
                psvMaxAE = 0;
                psvMax = 0;
                dutyMaxValAE.setVisibility(View.INVISIBLE);
                dutyMaxBlockAE.setVisibility(View.INVISIBLE);
                textMaxDutyAE.setVisibility(View.INVISIBLE);
                dutyStart = Math.round((calcosO - 1) * 100);
                dutyFinish = Math.round((calcosI) *100);
                dutyStart = dutyStart / 100;
                dutyFinish = dutyFinish / 100;
                if ((dutyStart <= 4.98) || (dutyStart > 17)) {
                    psvMax = 11;
//                    Toast.makeText(CrewRest.this,
//                            "Extensão do Duty não é permitida", Toast.LENGTH_LONG).show();
//                    imageExtension.setBackgroundResource(android.R.drawable.checkbox_on_background);
                } else {
                    if ((dutyStart >= 5) && (dutyStart <= 5.98)) {
                        if ((dutyStart >= 5) && (dutyStart <= 5.23)) {
                            psvMax = 12;
                        } else {
                            descontoPSV = Math.floor((dutyStart - 5) * 4f) / 4f; // arredondamento aos 15 abaixo
                            psvMax = 12 + descontoPSV;                           // contando a totalidade do PSV
                        }
                    } else {
                        if ((dutyStart >= 6) && (dutyStart <= 13.48)) {
                            psvMax = 13;
                        } else {
                            descontoPSV = Math.ceil(((dutyStart - 13.49) / 2) * 4f) / 4f; // arredondamento aos 15 acima
                            psvMax = 13 - descontoPSV;                                    // contando metade do PSV
                        }
                    }

                }
//                if (((dutyStart <= 6.23) || (dutyStart >= 13.5)) && (nsectores+2 >= 5) ){
//                    imageExtension.setBackgroundResource(android.R.drawable.checkbox_on_background);
//                }else {
//                    if (((dutyStart <= 6.23) || (dutyStart >= 15.5)) && (nsectores+2 >= 3)) {
//                        imageExtension.setBackgroundResource(android.R.drawable.checkbox_on_background);
//                    } else {
//                        if (((dutyStart <= 6.23) || (dutyStart >= 19.0)) && (nsectores+2 >= 1)) {
//                            imageExtension.setBackgroundResource(android.R.drawable.checkbox_on_background);
//                        }
//                    }
//                }
                psvMax = psvMax - nsectores * 0.5; //retira 30 minutos por cada sector acima dos 2
                if ((dutyStart <= 6) || (dutyStart >= 22))
                    psvMaxAE = 6;
                else
                    psvMaxAE = psvMax;
                psvMaxHora = (int) psvMax;
                psvMaxHoraAE = (int) psvMaxAE;
                psvMaxMin = Math.round((psvMax - (int) psvMax) * 60);
                psvMaxMinAE = Math.round((psvMaxAE - (int) psvMaxAE) * 60);
                dutyMaxCalcos = calcosO - 1 + psvMax;
                dutyMaxCalcosAE = calcosO - 1 + psvMaxAE;
                if (dutyMaxCalcos > 24) {
                    dutyMaxCalcos = dutyMaxCalcos - 24;
                }
                if (dutyMaxCalcosAE > 24) {
                    dutyMaxCalcosAE = dutyMaxCalcosAE - 24;
                }
                dutyMaxCalcosH = (int) dutyMaxCalcos;
                dutyMaxCalcosHAE = (int) dutyMaxCalcosAE;
                dutyMaxCalcosM = Math.round((dutyMaxCalcos - (int) dutyMaxCalcos) * 60);
                dutyMaxCalcosMAE = Math.round((dutyMaxCalcosAE - (int) dutyMaxCalcosAE) * 60);
                if (psvMaxAE == 6) {
                    dutyMaxValAE.setText(String.format("%02d", psvMaxHoraAE) + ":" + String.format("%02d", (int) psvMaxMinAE));
                    dutyMaxValAE.setVisibility(View.VISIBLE);
                    textMaxDutyAE.setText("If previous rest is bigger than 16:00 Max. Duty Blocks are " + String.format("%02d", dutyMaxCalcosHAE + 3) + ":" + String.format("%02d", (int) dutyMaxCalcosMAE));
                    textMaxDutyAE.setVisibility(View.VISIBLE);
                }
                dutyMaxVal.setText(String.format("%02d", psvMaxHora) + ":" + String.format("%02d", (int) psvMaxMin));
                dutyMaxVal.setVisibility(View.VISIBLE);
                dutyMaxBlock.setText(String.format("%02d", dutyMaxCalcosH) + ":" + String.format("%02d", (int) dutyMaxCalcosM));
                dutyMaxBlock.setVisibility(View.VISIBLE);
                dutyMaxBlockAE.setText(String.format("%02d", dutyMaxCalcosHAE) + ":" + String.format("%02d", (int) dutyMaxCalcosMAE));
                dutyMaxBlockAE.setVisibility(View.VISIBLE);
            }
        });

        /*SPLIT*/

        calcDescansoSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitDutyMaxTextAE.setVisibility(View.INVISIBLE);
                dutyStart = Math.round((calcosO - 1) * 100);
                dutyStart = dutyStart / 100;
                if (calcosO > calcosI)
                    calcosI = calcosI + 24;
                if (calcosISplit > calcosOSplit)
                    calcosOSplit = calcosOSplit + 24;

                double temptranspSplit = (ttransporteSplit * 2);
                temptranspSplit = temptranspSplit / 60;
                splitRest = calcosOSplit - calcosISplit;
                if (calcosOSplit>24)
                    calcosOSplit = calcosOSplit - 24; //Repõe a hora certa caso se utilize novamente
                if (splitRest < (temptranspSplit + 3 + 1 + 0.5)) { //Tempo de transporte + 3 horas + Pre & Post Flight duties (60 + 30)
                    dutyMaxBlockSplit.setText("FTL");
                    dutyMaxBlockSplit.setVisibility(View.VISIBLE);
                    dutyMaxSplit.setText("Ilegal");
                    dutyMaxSplit.setVisibility(View.VISIBLE);
                }else{
                    splitDutyMax = (splitRest/2) + 11;
                    splitDutyBlock = dutyStart + splitDutyMax;
                    double splitDutyBlockAE = dutyStart + 12.5;
                    if (splitDutyBlock > 24)
                        splitDutyBlock = splitDutyBlock - 24;
                    if (splitDutyBlockAE > 24)
                        splitDutyBlockAE = splitDutyBlockAE - 24;
                    int splitDutyMaxHora = (int) splitDutyMax;
                    int splitDutyBlockHora = (int) splitDutyBlock;
                    int splitDutyBlockHoraAE = (int) splitDutyBlockAE;
                    double splitDutyBlockMin = Math.round((splitDutyBlock - (int)splitDutyBlock) * 60);
                    double splitDutyBlockMinAE = Math.round((splitDutyBlockAE - (int)splitDutyBlockAE) * 60);
                    double splitDutyMaxMin = Math.round((splitDutyMax - (int)splitDutyMax) * 60);
                    dutyMaxBlockSplit.setText(String.format("%02d", splitDutyBlockHora) + ":" + String.format("%02d", (int) splitDutyBlockMin));
                    dutyMaxBlockSplit.setVisibility(View.VISIBLE);
                    dutyMaxSplit.setText(String.format("%02d", splitDutyMaxHora) + ":" + String.format("%02d", (int) splitDutyMaxMin));
                    dutyMaxSplit.setVisibility(View.VISIBLE);
                    if (splitDutyMax>12.5) {
                        splitDutyMaxTextAE.setText("Limited to 12:30 by AE, Max. Duty Blocks " + String.format("%02d", splitDutyBlockHoraAE) + ":" + String.format("%02d", (int) splitDutyBlockMinAE));
                        splitDutyMaxTextAE.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        textDutyFTL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Unforeseen ... Duty Extension");
                alertDialog.setMessage(Html.fromHtml("<b>"+"ORO.FTL.205 "+
                        "(f) Unforeseen"+"</b>"+" circumstances in flight operations" +
                        " — "+"<b>"+"Commander’s discretion"+"</b>" + "<br>"+ "<br>"+
                        "(1) The maximum daily FDP"+"<b>"+" may not be increased by more than 2 hours"+"</b>"+"...." + "<br>" +
                        "(3) The Commander"+"<b>"+" shall consult all crew members"+"</b>"+" on their alertness levels before deciding...."+ "<br>"+
                        "<br>" + "<b>" + "AMC1 ORO.FTL.205(f) FDP"+ "<br>" +"</b>" +
                        "(a) The exercise of Commander’s discretion "+"<b>"+"should be considered exceptional and should "
                        + "be avoided at home base and/or company hubs where standby or reserve crew members should be available."+"</b>"+ "<br>"+
                        //                       "(5) Where the increase of an FDP or reduction of a rest period "+"<b>"+"exceeds 1 hour, a copy"+"</b>"+" of the report,"
//                        + "..., shall be sent by the operator" +
//                                "to the competent authority "+"<b>"+"not later than 28 days"+"</b>"+" after the event."
                        "<br>"+"<b>"+"Dec. Lei 25/2022 Artigo 9º" +"</b>"+"<br>"+"a) Sempre que o PSV exceder em mais de "+"<b>"+
                        "30 minutos"+ "</b>" + " os respectivos limites, o operador de aeronave deve apresentar à ANAC o relatório justificativo no prazo máximo de" + "<b>" + " 28 dias seguidos." + "</b>" + "<br>" +
                        "b) O período de repouso subsequente deve ser acrescido"+"<b>"+" do dobro "
                        +"</b>"+"do tempo em que o PSV exceder os respectivos limites" +"<br>"));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        textRefTimeNextOfBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Limitação e Salvaguarda quando em WOCL");
                alertDialog.setMessage(Html.fromHtml("<b>"+"Cláusula 36.ª\n" + "<br>"+ "Limites do tempo de serviço de voo"+"</b>"+"<br>"+
                        "3. Sempre que a hora de "+"<b>"+"despertar (hora local)"+"</b>"+" ou apresentação, fora da base,\n" +
                        "se incluir no período "+"<b>"+"entre as 22h00 e as 06h00"+"</b>"+", o Piloto pode efetuar um\n" +
                        "tempo "+"<b>"+"máximo"+"</b>"+" de serviço de voo de "+"<b>"+"6 horas, excetuando nos casos"+"</b>"+" em que\n" +
                        "a hora de despertar (horal local) ou apresentação, seja precedida por um\n" +
                        "período de repouso "+"<b>"+"mínimo de 16 horas incluindo um período noturno de\n" +
                        "repouso"+"</b>"+", em que o Piloto pode efetuar um tempo "+"<b>"+"máximo"+"</b>"+" de serviço de voo\n" +
                        "de "+"<b>"+"9 horas."+"</b>"+"\n" + "<br>"+
                        "4. "+"<b>"+"Em irregularidade operacional ocorrida após saída de calços"+"</b>"+", o tempo de\n" +
                        "serviço de voo máximo será o fixado "+"<b>"+"no número 1 (FTL)"+"</b>"+" desta cláusula, para permitir\n" +
                        "o "+"<b>"+"regresso à base"+"</b>"+" mantendo os sectores planeados."));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });

        imageViewHelpSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(CrewRest.this, R.style.AlertDialog).create();
                alertDialog.setTitle("Acordo de Empresa SIPLA");
                alertDialog.setMessage(Html.fromHtml("<b>" + "Claúsula 43.º" + "<br>" +
                        "Split-Duty" + "</b>" + "<br>"+"<br>"+
                        "2. O período de serviço de voo repartido (split-duty) não poderá "+"<b>"+"exceder as doze\n" +
                        "horas e trinta minutos (12:30)."+"</b>"+"<br>"+
                        "5. Os períodos de serviço de voo que "+"<b>"+"incluam, no todo ou em parte, o período\n" +
                        "crítico do ritmo circadiano"+"</b>"+" só podem ocorrer "+"<b>"+"uma vez em cada quinzena."+"</b>" +"<br>"+
                        "6. "+"<b>"+"Não é permitida a operação"+"</b>"+" em aeroportos de "+"<b>"+"Categoria C"+"</b>"+" com recurso a\n" +
                        "períodos de serviço de voo que "+"<b>"+"incluam, no todo ou em parte, o período crítico\n" +
                        "do ritmo circadiano.\n" +"</b>"+ "<br>"+
                        "7. O período de "+"<b>"+"repouso após"+"</b>"+" o serviço de voo repartido "+"<b>"+"(split-duty)"+"</b>"+" terá a\n" +
                        "duração mínima de "+"<b>"+"36 horas."+"</b>"));
                // Alert dialog button
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Alert dialog action goes here
                                // onClick button code here
                                dialog.dismiss();// use dismiss to cancel alert dialog
                            }
                        });
                alertDialog.show();
            }
        });
    }

    private void showdialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Disclaimer");
        Button dialogButton = (Button) dialog.findViewById(R.id.button1);
        dialogcb= (CheckBox) dialog.findViewById(R.id.checkBox1);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accepted = dialogcb.isChecked();
                SharedPreferences.Editor edit=prefs.edit();
                edit.putBoolean("show_again",!accepted);
                edit.commit();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
