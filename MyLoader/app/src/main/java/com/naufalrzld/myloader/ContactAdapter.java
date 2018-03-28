package com.naufalrzld.myloader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Naufal on 28/03/2018.
 */

public class ContactAdapter extends CursorAdapter {
    public ContactAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_row_contact, viewGroup, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {
            TextView tvName = (TextView)view.findViewById(R.id.tv_item_name);
            CircleImageView imgUser = (CircleImageView)view.findViewById(R.id.img_item_user);
            RelativeLayout rlItem = (RelativeLayout)view.findViewById(R.id.rl_item);
            imgUser.setImageResource(R.drawable.ic_person_black_48dp);
            tvName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));

            if (cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI)) != null) {
                imgUser.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_URI))));
            } else {
                imgUser.setImageResource(R.drawable.ic_person_black_48dp);
            }
        }
    }
}
