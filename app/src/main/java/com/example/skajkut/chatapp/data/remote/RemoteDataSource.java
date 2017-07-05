package com.example.skajkut.chatapp.data.remote;

import android.net.Uri;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.Message;
import com.example.skajkut.chatapp.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Stefan Kajkut on 6/26/2017.
 * Contact me on stefan.kajkutsf@gmail.com.
 */

public class RemoteDataSource extends DataSource {

    private static RemoteDataSource instance = null;
    private static final String CONVERSATION_LIST = "conversationlist";
    private static final String CONVERSATIONS = "conversations";
    private static final String FAVORITE_FRIENDS = "favoritefriends";
    private static final String FRIEND_LIST = "friendlist";
    private static final String USERS = "users";
    private static final String MESSAGES = "messages";
    private static final String LASTMESSAGE = "lastMessage";



    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    final User[] currentUser = {new User()};
    final Integer[] counter = {0,0,0};

    private RemoteDataSource() {
        super();
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public static RemoteDataSource getInstance() {
        if(instance == null) {
            instance = new RemoteDataSource();
        }

        return instance;
    }

    public String getCurrentUserID(){
        return firebaseAuth.getCurrentUser().getUid();
    }

    public FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }



    @Override
    public void getConversationList(String userID, final GetConversationListCallback callback) {
        final List<String> conversationList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference()
                .child(CONVERSATION_LIST)
                .child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getValue(String.class);
                    conversationList.add(key);
                }
                callback.onSuccess(conversationList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.NETWORK_ERROR:
                        callback.onNetworkFailure();
                        break;
                    default:
                        callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void getConversation(String conversationID, final GetConversationCallback callback) {
        databaseReference = firebaseDatabase.getReference()
                .child(CONVERSATIONS)
                .child(conversationID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Conversation conversation = null;
                /*for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    conversation = snapshot.getValue(Conversation.class);
                }*/
                conversation = dataSnapshot.getValue(Conversation.class);
                callback.onSuccess(conversation);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()) {
                    case DatabaseError.NETWORK_ERROR:
                        callback.onNetworkFailure();
                        break;
                    default:
                        callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void getFriendList(final String userID, final GetFriendListCallback callback)  {
        final List<User> userList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference(FRIEND_LIST)
                .child(userID);

        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        DatabaseReference userRef = firebaseDatabase.getReference().child(USERS);
                        Query q = userRef.orderByKey().equalTo(snapshot.getKey());

                        q.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    User u = snap.getValue(User.class);
                                    u.setId(snap.getKey());
                                    userList.add(u);
                                }
                                callback.onSuccess(userList);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                switch (databaseError.getCode()) {
                                    case DatabaseError.NETWORK_ERROR:
                                        callback.onNetworkFailure();
                                        break;
                                    default:
                                        callback.onFailure(databaseError.toException());
                                }
                            }
                        });


                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    switch (databaseError.getCode()) {
                        case DatabaseError.NETWORK_ERROR:
                            callback.onNetworkFailure();
                            break;
                        default:
                            callback.onFailure(databaseError.toException());
                    }
                }
            });
        callback.onSuccess(userList);

    }

    @Override
    public void getFavoriteList(String userID, final GetFavoriteListCallback callback) {
        final List<User> userList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference(FAVORITE_FRIENDS).child(userID);
        if (databaseReference!=null) {
            databaseReference.keepSynced(true);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount()==0){
                        callback.onEmptyList();
                        return;
                    }
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        DatabaseReference userRef = firebaseDatabase.getReference().child(USERS);
                        Query q = userRef.orderByKey().equalTo(snapshot.getKey());

                        q.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                    User u = snap.getValue(User.class);
                                    u.setId(snap.getKey());
                                    userList.add(u);
                                }
                                callback.onSuccess(userList);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                callback.onFailure(null);
                            }
                        });


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    switch (databaseError.getCode()) {
                        case DatabaseError.NETWORK_ERROR:
                            callback.onNetworkFailure();
                            break;
                        default:
                            callback.onFailure(databaseError.toException());
                    }
                }
            });
        }

    }

    // TODO
    private void test(DatabaseError databaseError, GetEntityCallback callback) {
        switch (databaseError.getCode()){
            case DatabaseError.NETWORK_ERROR:
                callback.onNetworkFailure();
                break;
            default:
                callback.onFailure(databaseError.toException());
        }
    }

    @Override
    public void getUserByID(String userID, final GetUserCallback callback) {
        databaseReference = firebaseDatabase
                .getReference(USERS)
                .child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    user.setId(dataSnapshot.getKey());
                }
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                test(databaseError, callback);
            }
        });
    }

    @Override
    public void getUserByUsername(String username, final GetUserCallback callback) {
        databaseReference = firebaseDatabase
                .getReference(USERS)
                .child(username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (databaseError.getCode()){
                    case DatabaseError.NETWORK_ERROR:
                        callback.onNetworkFailure();
                        break;
                    default:
                        callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void getAllUsers(final GetUsersCallback callback) {
        final List<User> users = new ArrayList<>();
        databaseReference = firebaseDatabase
                .getReference(USERS);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
                callback.onSuccess(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (DatabaseError.NETWORK_ERROR){
                    case DatabaseError.NETWORK_ERROR:
                        callback.onNetworkFailure();
                        break;
                    default:
                        callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void createUser(String firstname, String lastname, String username,
                           String password, String email, AddUserCallback callback) {
        User user = new User(firstname, lastname, username, password, email);
        databaseReference = firebaseDatabase
                .getReference(USERS);

        String uID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(uID).setValue(user);
        callback.onSuccess(user);

    }

    @Override
    public void searchUsers(String searchValue, String searchQuery,
                            final SearchUsersCallback callback) {

        final Set<User> users = new HashSet<>();
        databaseReference = firebaseDatabase
                .getReference(USERS);
        Query query = databaseReference.orderByChild(searchQuery)
                .startAt(searchValue)
                .endAt(searchValue + "\uf8ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = null;
                for(DataSnapshot snap : dataSnapshot.getChildren()) {
                    user = snap.getValue(User.class);
                    user.setId(snap.getKey());
                    users.add(user);
                }
                callback.onSuccess(new ArrayList<User>(users));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                switch (DatabaseError.NETWORK_ERROR){
                    case DatabaseError.NETWORK_ERROR:
                        callback.onNetworkFailure();
                        break;
                    default:
                        callback.onFailure(databaseError.toException());
                }
            }
        });
    }

    @Override
    public void createUserFromProvider(String firstname, String lastname, String email, Uri photoUrl, AddUserFromProviderCallback callback) {
        String photo = photoUrl.toString();
        User user = new User(firstname, lastname, email, photo);
        databaseReference =
                firebaseDatabase.getReference(USERS);
        String uID = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child(uID).setValue(user);
        callback.onSuccess(user);
    }

    /*@Override
    public void getCurrentLoggedUser(final GetCurrentLoggedUserCallback callback) {
        String uID = getCurrentUserID();

        getFavoriteList(uID, new GetFavoriteListCallback() {
            @Override
            public void onSuccess(List<User> users) {
                currentUser[0].setFavoriteList(convertListToMap(users));
                counter[1]++;
                Log.d("TAG", "getFavoriteList - onSuccess: counter++ " + users.size());
                checkAllConditions(counter[0], callback);

            }

            @Override
            public void onEmptyList() {
                counter[1]++;
                Log.d("TAG", "getFavoriteList - onEmptyList: counter++");
                checkAllConditions(counter[0], callback);

            }

            @Override
            public void onFailure(Throwable throwable) {
                counter[1]++;
                Log.d("TAG", "getFavoriteList - onFailure: counter++");
                checkAllConditions(counter[0], callback);

            }

            @Override
            public void onNetworkFailure() {
                counter[0]++;
                Log.d("TAG", "getFavoriteList - onNetworkFailure: counter++");
                checkAllConditions(counter[0], callback);
            }

        });

        getFriendList(uID, new GetFriendListCallback() {
            @Override
            public void onSuccess(List<User> users) {
                currentUser[0].setFriendList(convertListToMap(users));
                counter[2]++;
                Log.d("TAG", "getFriendList - onSuccess: counter++ " + users.size());
                checkAllConditions(counter[0], callback);
            }

            @Override
            public void onFailure(Throwable throwable) {
                counter[2]++;
                Log.d("TAG", "getFriendList - onFailure: counter++");
                checkAllConditions(counter[0], callback);
            }

            @Override
            public void onNetworkFailure() {
                counter[2]++;
                Log.d("TAG", "getFriendList - onNetworkFailure: counter++");
                checkAllConditions(counter[0], callback);
            }
        });

    }

    private Map<String, User> convertListToMap(List<User> users) {
        Map<String, User> usersMap = new HashMap<String, User>();
        for (User u : users) usersMap.put(u.getId(), u);

        return usersMap;
    }

    private void checkAllConditions(Integer i, GetCurrentLoggedUserCallback callback){
        if(counter[0] > 0 && counter[1] > 0 && counter[2] > 0) {
            counter[0] = 0; counter[1] = 0; counter[2] = 0;
            callback.onSuccess(currentUser[0]);
        }
    }*/

    @Override
    public void addFavorite(User user, AddFavoriteCallback callback) {
        databaseReference = firebaseDatabase.getReference(FAVORITE_FRIENDS);
        String currentUser = getCurrentUserID();
        databaseReference.child(currentUser).child(user.getId()).removeValue();
        callback.onSuccess(user);
    }

    @Override
    public void removeFavorite(User user, RemoveFavoriteCallback callback) {
        databaseReference = firebaseDatabase.getReference(FAVORITE_FRIENDS);
        String currentUser = getCurrentUserID();
        databaseReference.child(currentUser).child(user.getId()).setValue(user.getUsername());
        callback.onSuccess(user);
    }

    @Override
    public void getMessages(Conversation conversation, final GetMessagesCallback callback) {
        databaseReference = firebaseDatabase.getReference(CONVERSATIONS).child(conversation.getId()).child(MESSAGES);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot i : dataSnapshot.getChildren()){
                    Message m = i.getValue(Message.class);
                    messages.add(m);
                }
                callback.onSuccess(messages);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void sendMessage(String message, Conversation conversation, SendMessageCallback callback) {

        String uID = getCurrentUserID();

        FirebaseUser user = getCurrentUser();

        Date date = new Date();

        Message m = new Message();
        m.setSender(user.getDisplayName());
        m.setText(message);
        m.setDateTime(date);

        /*if (conversation.getMessageList() == null) {
            conversation.setMessageList(new ArrayList<Message>());
        }*/

        conversation.getMessageList().add(m);

        databaseReference = firebaseDatabase.getReference(CONVERSATIONS);
        databaseReference.child(conversation.getId()).child(MESSAGES).setValue(conversation.getMessageList());
       /* databaseReference.child(cID).child(LASTMESSAGE).setValue(c.getLastMessage());
        databaseReference.child(cID).child(USERS).setValue(c.getUsers());*/

    }

    @Override
    public void createConversation(Conversation c, CreateConversationCallback callback) {

        databaseReference = firebaseDatabase.getReference(CONVERSATIONS);
        String id = databaseReference.push().getKey();
        c.setId(id);

        Message m2 = new Message();
        m2.getDateTime();
        m2.setText("Hello from user2");
        m2.setSender("Levi Test");

        databaseReference.child(id).setValue(c);
        databaseReference.child(id).child(USERS).setValue(c.getUsers());
        databaseReference.child(id).child(LASTMESSAGE).setValue(m2);
        callback.onSuccess(c);
    }

    @Override
    public void createConversationForUser(Conversation c) {

        List<String> list = new ArrayList<>();
        list.add(c.getId());



        databaseReference = firebaseDatabase.getReference(CONVERSATION_LIST);
        for(Map.Entry<String, String> users : c.getUsers().entrySet()){
            String userId = users.getKey();

            databaseReference.child(userId).setValue(c.getId());
        }

    }
}
