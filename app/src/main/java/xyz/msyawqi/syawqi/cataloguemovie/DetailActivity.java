package xyz.msyawqi.syawqi.cataloguemovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import xyz.msyawqi.syawqi.cataloguemovie.database.DatabaseContract;
import xyz.msyawqi.syawqi.cataloguemovie.database.Favorite;
import xyz.msyawqi.syawqi.cataloguemovie.database.FavoriteHelper;

import static xyz.msyawqi.syawqi.cataloguemovie.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {
    private Context context;
    private TextView tv_name;
    private TextView tv_date;
    private TextView tv_desc ;
    private ImageView imageView;
    private Button button;
    private LayoutInflater mInflater;

    private Favorite favorite;
    private int position;
    private FavoriteHelper favoriteHelper;

    public static int RESULT_ADD = 101;

    Integer id;
    String name;
    String date;
    String desc;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        button = findViewById(R.id.btn_favorite);

         id =  getIntent().getIntExtra("id", 0);
         name = getIntent().getStringExtra("name");
         date = getIntent().getStringExtra("date");
         desc = getIntent().getStringExtra("desc");
         image = getIntent().getStringExtra("image");


        tv_name = findViewById(R.id.tv_title_detail);
        tv_date = findViewById(R.id.tv_date_detail);
        tv_desc = findViewById(R.id.tv_desc_detail);
        imageView = findViewById(R.id.img_list_detail);

        tv_name.setText(name);
        tv_date.setText(date);
        tv_desc.setText(desc);

        Glide.with(this)
                .load(image)
                .override(120,120)
                .crossFade()
                .into(imageView);

        favoriteHelper = new FavoriteHelper(this);
        try {
            favoriteHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();

//                Favorite newFavorite = new Favorite();

                values.put(DatabaseContract.FavoriteColumns.MOVIEID, id);
                values.put(DatabaseContract.FavoriteColumns.TITLE, name);
                values.put(DatabaseContract.FavoriteColumns.DESCRIPTION, desc);
                values.put(DatabaseContract.FavoriteColumns.DATE, date);
                values.put(DatabaseContract.FavoriteColumns.IMAGE, image);
//                newFavorite.setMovie(id);
//                newFavorite.setTitle(name);
//                newFavorite.setDesc(desc);
//                newFavorite.setDate(date);
//                newFavorite.setImage(image);

                getContentResolver().insert(CONTENT_URI,values);

                setResult(RESULT_ADD);
                finish();
            }
        });
    }
}
