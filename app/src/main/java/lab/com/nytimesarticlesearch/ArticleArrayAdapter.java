package lab.com.nytimesarticlesearch;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Shabnam on 2/11/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article>{
    public ArticleArrayAdapter(Context context, List <Article> articls){

        super(context, android.R.layout.simple_list_item_1, articls);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // gets the data item for the position
        Article article=this.getItem(position);
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }
        ImageView imageView= (ImageView) convertView.findViewById(R.id.ivImage);
        imageView.setImageResource(0);
        TextView tvTitle= (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());
        String thumbnail= article.getThumbnail();
        if(!TextUtils.isEmpty(thumbnail)){
            Picasso.with(getContext()).load(thumbnail).into(imageView);
        }
        return convertView;
    }
}
