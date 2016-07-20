package huang.com.myandroidtools;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import huang.com.staggeredgrid.StaggeredGridView;
import huang.com.staggeredgrid.utils.DynamicHeightTextView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.grid_view)
    StaggeredGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridAdapter adapter = new GridAdapter(MainActivity.this);
        gridView.setAdapter(adapter);
    }

    class GridAdapter extends BaseAdapter {
        private Context context;
        private  final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
        public GridAdapter(Context context) {
            this.context = context;
        }
        private final Random mRandom = new Random();;
        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.adapter_grid_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }else {
                holder = (ViewHolder)view.getTag();
            }
            if(i % 2 == 0){
//                holder.img.setImageResource(R.drawable.guide_2);
                view.setBackgroundColor(Color.RED);
            }else {
//                holder.img.setImageResource(R.drawable.guide_3);
                view.setBackgroundColor(Color.BLUE);
            }
            double positionHeight = getPositionRatio(i);
            holder.tv.setHeightRatio(positionHeight);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.img)
            ImageView img;
            @BindView(R.id.tv)
            DynamicHeightTextView tv;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
        private double getPositionRatio(final int position) {
            double ratio = sPositionHeightRatios.get(position, 0.0);
            // if not yet done generate and stash the columns height
            // in our real world scenario this will be determined by
            // some match based on the known height and width of the image
            // and maybe a helpful way to get the column height!
            if (ratio == 0) {
                ratio = getRandomHeightRatio();
                sPositionHeightRatios.append(position, ratio);
            }
            return ratio;
        }

        private double getRandomHeightRatio() {
            return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
        }
    }
}
