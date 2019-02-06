# GenericAdapter

This project is an Android library to reduce the amount of code for using RecyclerView, Recycler View Adapters and ViewHolder.
It _re_-introduces the *ListFragment* in a new form and simplifies the use of Adapter.

*Generic Adapters* are filterable by default.

## Getting Started
The idea of this repository is to define

### Installing
Clone this repository into your project or wherever you want in you computer.
```
git clone https://github.com/madamadore/GenericAdapter.git /path/to/your/folder
```

Add the dependency using your `settings.gradle` file:
```
// settings.gradle
include ':app', ':genericadapter'
// ...
project(':genericadapter').projectDir = new File("/path/to/your/folder/genericadapter")
```

and
```
// build.gradle (Module: app)
dependencies {
    // ...
    implementation project(':genericadapter')
    // ...
}
```

### Getting Started

After installing the module. Follow the subsequent steps.

#### 1. Define a _Model_
A Model is basically a POJO (_Plain Old Java Object_). It *must* implements _Parcelable_ interface.

```
class MyModel implements Parcelable {
    // ...
    public MyModel() {}

    private MyModel(Parcel in) {
        // ...
    }

    public static final Creator<MyModel> CREATOR = new Creator<MyModel>() {
            @Override
            public MyModel createFromParcel(Parcel in) {
                return new MyModel(in);
            }

            @Override
            public MyModel[] newArray(int size) {
                return new MyModel[size];
            }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // ...
    }
}
```
#### 2. Define a Layout

Example of _R.layout.my_list_item_:
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="?android:attr/listPreferredItemHeight"
    android:layout_marginLeft="2dp"
    android:layout_marginRight="2dp"
    android:layout_marginTop="50dp"
    android:layout_marginBottom="4dp"
	android:padding="5dp"
	android:id="@+id/background_list_item">

    <ImageView
        android:id="@+id/list_image"
        android:layout_width="50dip"
        android:layout_height="50dip"
        android:layout_marginTop="2dip"
   		android:layout_marginLeft="3dip"
   		android:layout_marginStart="3dip"
   		android:background="@android:color/white"
        android:scaleType="centerCrop" />

    <TextView
        android:id="@+id/list_name"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center_vertical|start"
        android:textSize="20sp"
        android:text="testo"
        android:layout_toEndOf="@id/list_image"
        android:color="@android:color/black"
        android:layout_marginStart="5dp"
		android:paddingTop="8dp"
		android:paddingStart="6dp"
        android:background="@android:color/white"
        />

</RelativeLayout>
```

#### 3. Define a *ViewHolder*

E.g.
```
public class MyViewHolder extends RecyclerView.ViewHolder {

    final TextView name;
    final ImageView image;
    final RelativeLayout background;

    public static final int ITEM_LAYOUT_RESOURCE = R.layout.list_item;

    public MyViewHolder(@NonNull View view) {
        super(view);

        name = view.findViewById(R.id.list_name);
        image = view.findViewById(R.id.list_image);
        background = view.findViewById(R.id.background_list_item);

        Context context = view.getContext();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), Constant.FONT_BODY);
        name.setTypeface(tf);
    }
}
```

#### 4. Define an *Adapter*

```
public class MyAdapter extends ItemArrayAdapter<MyModel, MyViewHolder>
{
	public MyAdapter(Context context, OnItemRecyclerListener listener,
						   List<MyModel> objects) {
		super(context, listener, MyViewHolder.ITEM_LAYOUT_RESOURCE, objects, null);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, MyModel item) {
        int imageResourceId = item.getIconResource();

        holder.image.setImageResource(imageResourceId);
        holder.name.setText(item.getName());
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.my_list_item, null);
		return new MyViewHolder(view);
	}

	protected Filter mFilter = new Filter() {

		@Override
		protected FilterResults performFiltering(CharSequence query) {
			MyDatabase db = new MyDatabase(context);
			List<MyModel> models = db.getModels(query.toString()); // ...
			db.close();

			FilterResults results = new FilterResults();
			results.values = models;
			results.count = models.size();

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filteredData = (List<MyModel>) results.values;
			notifyDataSetChanged();
		}
	};

	@Override
	public Filter getFilter() {
		return mFilter;
	}
}
```

#### 5. Define an *ListFragment*

A *ListFragment* class signature:
```
public class MyListFragment extends ListFragment<MyModel, MyViewHolder>
		implements RecyclerViewAdapter.OnItemRecyclerListener<MyModel> {

		// ...
}
```
## Authors

M. Avanzini (www.matteoavanzini.it)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details