package info.javaway.greendaotutorial;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import info.javaway.greendaotutorial.model.Cat;
import info.javaway.greendaotutorial.model.CatDao;
import info.javaway.greendaotutorial.model.DaoSession;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFab;
    private EditText mCatNameEditText;
    private EditText mCatBreedEditText;
    private RecyclerView mRecyclerView;

    private CatAdapter adapter;
    private List<Cat> mCats;

    public CatDao getCatDao() {
        return mCatDao;
    }

    private CatDao mCatDao;
    private Query<Cat> catsQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();

        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        mCatDao = daoSession.getCatDao();

        catsQuery = mCatDao.queryBuilder().orderAsc(CatDao.Properties.Name).build();
        updateCats();
    }

    public void updateCats() {
        mCats = catsQuery.list();
        adapter.setCats(mCats);
    }

    private void setupViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mCatNameEditText = (EditText) findViewById(R.id.cat_name_et);
        mCatBreedEditText = (EditText) findViewById(R.id.cat_breed_et);

        mCats = new ArrayList<>();
        adapter = new CatAdapter(mCats, getLayoutInflater(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCat();

            }
        });
    }

    private void addCat() {
        String name = mCatNameEditText.getText().toString();
        String breed = mCatBreedEditText.getText().toString();
        mCatNameEditText.setText("");
        mCatBreedEditText.setText("");
        mCatNameEditText.clearFocus();
        mCatBreedEditText.clearFocus();

        if (name.trim().equals("") || breed.trim().equals("")) {
            Toast.makeText(MainActivity.this, "Имя или порода не заполнены", Toast.LENGTH_SHORT).show();
            return;
        }
        Cat cat = new Cat();
        cat.setName(name);
        cat.setBreed(breed);
        mCatDao.insert(cat);
        updateCats();
    }


}
