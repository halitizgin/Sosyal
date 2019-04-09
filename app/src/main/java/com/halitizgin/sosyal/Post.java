package com.halitizgin.sosyal;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Post extends ArrayAdapter<String>
{
    private final ArrayList<String> Email;
    private final ArrayList<String> Title;
    private final ArrayList<String> Image;
    private final Activity context;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View PostView = layoutInflater.inflate(R.layout.post_view, null, true);

        TextView userText = PostView.findViewById(R.id.userText);
        TextView titleText = PostView.findViewById(R.id.titleText);
        ImageView imageView = PostView.findViewById(R.id.imageView);

        userText.setText(Email.get(position));
        titleText.setText(Title.get(position));
        Picasso.get().load(Image.get(position)).into(imageView);

        return PostView;
    }

    public Post(ArrayList<String> email, ArrayList<String> title, ArrayList<String> image, Activity context) {
        super(context, R.layout.post_view, email);
        Email = email;
        Title = title;
        Image = image;
        this.context = context;
    }
}
