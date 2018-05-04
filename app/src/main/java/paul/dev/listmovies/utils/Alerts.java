package paul.dev.listmovies.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by paulotrujillo on 5/3/18.
 */

public class Alerts extends ProgressDialog {
    public Alerts(Context context) {
        super(context);
    }

    ProgressDialog progressDialog;

    public  void showAlert(String title, String message){

        progressDialog = ProgressDialog.show(getContext(), "Cargando Películas","Por favor espere...", true);
    }

    public  void close(){
        progressDialog.dismiss();
    }
}
