package com.example.skajkut.chatapp.data.remote;

import com.example.skajkut.chatapp.data.model.Conversation;
import com.example.skajkut.chatapp.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

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
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    conversation = snapshot.getValue(Conversation.class);
                }
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
    public void getFriendList(String userID, final GetFriendListCallback callback) {
        final List<User> userList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference()
                .child(FRIEND_LIST)
                .child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
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

    @Override
    public void getFavoriteList(String userID, final GetFavoriteListCallback callback) {
        final List<User> userList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference()
                .child(FAVORITE_FRIENDS)
                .child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
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
}
