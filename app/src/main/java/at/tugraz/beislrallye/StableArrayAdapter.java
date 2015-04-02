/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package at.tugraz.beislrallye;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<Place> {
    final int INVALID_ID = -1;
    private List<Place> objects;
    private ArrayList<String> distances;
    private ArrayList<String> durations;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

    public StableArrayAdapter(Context context, int textViewResourceId, List<Place> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i).getName(), i);
        }
    }

    public StableArrayAdapter(Context context, int textViewResourceId, List<Place> objects, ArrayList<String> distances, ArrayList<String> durations) {
        super(context, textViewResourceId, objects);
        this.distances = distances;
        this.durations = durations;
        this.objects = objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i).getName(), i);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.places_listview_item, null);
        }

        Place place = getItem(position);

        ((TextView)v.findViewById(R.id.item_name)).setText(place.getName());
        ((TextView)v.findViewById(R.id.item_address)).setText(place.getAddress());
        ((TextView)v.findViewById(R.id.distance)).setText(distances.get(position));
        ((TextView)v.findViewById(R.id.duration)).setText(durations.get(position));
        v.findViewById(R.id.visited_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageView)v).setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_place_visited));
            }
        });

        v.findViewById(R.id.visited_image).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ((ImageView)v).setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_place_unvisited));
                return true;
            }
        });
        if(place.getType() == PlaceType.LOCATION)
            v.findViewById(R.id.visited_image).setVisibility(View.GONE);

        return v;

    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position).getName();
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void swapElements(int indexOne, int indexTwo) {
        Place temp = objects.get(indexOne);
        objects.set(indexOne, objects.get(indexTwo));
        objects.set(indexTwo, temp);
    }

    public List<Place> getItems() {
        return objects;
    }
}
