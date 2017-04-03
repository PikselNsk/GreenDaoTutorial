package info.javaway.greendaotutorial;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.javaway.greendaotutorial.model.Cat;


public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder> {

    private List<Cat> mCats;
    private LayoutInflater mInflater;
    private AppCompatActivity mAppCompatActivity;

    public CatAdapter(List<Cat> cats, LayoutInflater inflater, AppCompatActivity activity) {
        mCats = cats;
        mInflater = inflater;
        mAppCompatActivity = activity;
    }

    @Override
    public CatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cat_item, parent, false);
        return new CatHolder(view);
    }

    @Override
    public void onBindViewHolder(CatHolder holder, int position) {
        Cat cat = mCats.get(position);
        holder.bindCat(cat);
    }

    @Override
    public int getItemCount() {
        return mCats.size();
    }

    public void setCats(List<Cat> cats) {
        mCats = cats;
        notifyDataSetChanged();
    }

    class CatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mCatName;
        private TextView mCatBreed;
        private Cat cat;


        public CatHolder(View itemView) {
            super(itemView);
            mCatName = (TextView) itemView.findViewById(R.id.cat_name);
            mCatBreed = (TextView) itemView.findViewById(R.id.cat_breed);
            itemView.setOnClickListener(this);

        }

        public void bindCat(Cat cat) {
            this.cat = cat;
            mCatName.setText(cat.getName());
            mCatBreed.setText(cat.getBreed());
        }

        @Override
        public void onClick(View view) {
            Cat cat = mCats.get(getLayoutPosition());
            Long catId = cat.getId();
            ((MainActivity)mAppCompatActivity).getCatDao().deleteByKey(catId);
            ((MainActivity)mAppCompatActivity).updateCats();
        }
    }
}
