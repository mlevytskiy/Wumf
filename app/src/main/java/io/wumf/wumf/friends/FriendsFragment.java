package io.wumf.wumf.friends;

import io.wumf.wumf.friends.core.AnyFragment;

/**
 * Created by max on 02.06.15.
 */
public class FriendsFragment extends AnyFragment {

//    public static final String KEY_FRIENDS = "keyFriends";
//
//    @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            final View view = inflater.inflate(R.layout.fragment_friends, container, false);
//        TextView empty = (TextView) view.findViewById(R.id.empty);
//        Typeface font = Typeface.createFromAsset(view.getResources().getAssets(), "Roboto-Light.ttf");
//        empty.setTypeface(font);
//        ListView listView = (ListView) view.findViewById(R.id.list_view);
//        listView.setEmptyView(empty);
//        final ArrayList<Friend> friends = getArguments().getParcelableArrayList(KEY_FRIENDS);
//        final FriendsAdapter friendsAdapter = new FriendsAdapter(friends, true, getActivity());
//        listView.setAdapter(friendsAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Friend friend = friendsAdapter.getItem(position);
//                if (friend == null) {
//                    //doNothing
//                } else {
//                    openFriendDetail(friend);
//                }
//            }
//        });
//
//        Button buttonUp = (Button) view.findViewById(R.id.buttonUp);
//        buttonUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isDirectSort = friendsAdapter.getDirectAlphabetSort();
//                friendsAdapter.update(friends, !isDirectSort);
//                friendsAdapter.notifyDataSetChanged();
//                ((ImageView) view.findViewById(R.id.image_view)).setImageDrawable(
//                        isDirectSort ? getResources().
//                                getDrawable(R.drawable.arrow_down) :
//                                getResources().getDrawable(R.drawable.arrow_up));
//            }
//        });
//        return view;
//    }
//
//    private void openFriendDetail(Friend friend) {
//        if (friend.getApps() == null) {
//            FriendDetailWithoutWumfFragment friendDetail = new FriendDetailWithoutWumfFragment();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(FriendDetailWithoutWumfFragment.KEY_FRIEND, friend);
//            friendDetail.setArguments(bundle);
//            startFragment(friendDetail);
//        } else {
//            FriendDetailWithWumfFragment2 friendDetail = new FriendDetailWithWumfFragment2();
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(FriendDetailWithWumfFragment2.KEY_FRIEND, friend);
//            friendDetail.setArguments(bundle);
//            startFragment(friendDetail);
//        }
//    }
//
//    public void onStart() {
//        onStartWithShowHomeUp();
//    }
}
