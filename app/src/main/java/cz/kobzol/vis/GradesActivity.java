package cz.kobzol.vis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.kobzol.vis.dao.GradeDTO;
import cz.kobzol.vis.net.NetworkCommunicator;
import cz.kobzol.vis.net.ServiceResponse;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        NetworkCommunicator.getInstance().testGetGrades(new ServiceResponse<List<GradeDTO>>() {
            @Override
            public void onResult(List<GradeDTO> result) {
                ProgressBar bar = (ProgressBar) GradesActivity.this.findViewById(R.id.progressBar);
                bar.setVisibility(View.INVISIBLE);

                List<String> values = new ArrayList<String>();

                for (int i = 0; i < result.size(); i++)
                {
                    values.add("Value: " + result.get(i).Value + ", Weight: " + result.get(i).Weight);
                }

                ListView listView = (ListView) GradesActivity.this.findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(GradesActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, values);


                listView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable exception) {
                Toast.makeText(GradesActivity.this, "An error occured while downloading grades", Toast.LENGTH_LONG).show();
            }
        });
    }
}
