package com.github.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zengven
 * date: 2017/8/21
 * Desc: Utility to request and check System permissions for apps targeting Android M (API >= 23).
 */
public class EasyPermissions {

    private static final String TAG = "EasyPermissions";

    /**
     * Callback interface to receive the results of {@code EasyPermissions.requestPermissions()}
     * calls.
     */
    public interface PermissionCallback {

        void onPermissionsFullGranted(int requestCode);

        void onPermissionsGranted(int requestCode, List<String> perms);

        void onPermissionsDenied(int requestCode, List<String> perms);

    }

    /**
     * check and request permission
     *
     * @param activity the calling activity.
     * @param perms    one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not yet granted and request permission.
     */
    public static boolean checkAndRequestPermissions(@NonNull Activity activity, int requestCode, @NonNull String... perms) {
        if (hasPermissions(activity.getApplicationContext(), perms)) {
            return true;
        }
        requestPermissions(activity, requestCode, perms);
        return false;
    }

    /**
     * check and request permission
     *
     * @param fragment the calling fragment.
     * @param perms    one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not yet granted and request permission.
     */
    public static boolean checkAndRequestPermissions(@NonNull Fragment fragment, int requestCode, @NonNull String... perms) {
        Context context = fragment.getContext();
        if (null == context) {
            return false;
        }
        if (hasPermissions(context.getApplicationContext(), perms)) {
            return true;
        }
        requestPermissions(fragment, requestCode, perms);
        return false;
    }

    /**
     * Check if the calling context has a set of permissions.
     *
     * @param context the calling context.
     * @param perms   one ore more permissions, such as {@link Manifest.permission#CAMERA}.
     * @return true if all permissions are already granted, false if at least one permission is not
     * yet granted.
     * @see Manifest.permission
     */
    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        // Always return true for SDK < M, let the system deal with the permissions
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");

            // DANGER ZONE!!! Changing this will break the library.
            return true;
        }

        // Null context may be passed if we have detected Low API (less than M) so getting
        // to this point with a null context should not be possible.
        if (context == null) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Request permissions from an Activity with standard OK/Cancel buttons.
     *
     * @param host
     * @param requestCode
     * @param perms
     */
    public static void requestPermissions(@NonNull Activity host, int requestCode, @NonNull String... perms) {
        if (perms.length <= 0)
            return;
        ArrayList<String> permList = new ArrayList<>();
        for (String perm : perms) {
            if (ActivityCompat.checkSelfPermission(host.getApplicationContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                permList.add(perm);
            }
        }
        ActivityCompat.requestPermissions(host, permList.toArray(new String[permList.size()]), requestCode);
    }

    /**
     * Request permissions from an Fragment with standard OK/Cancel buttons.
     *
     * @param host
     * @param requestCode
     * @param perms
     */
    public static void requestPermissions(@NonNull Fragment host, int requestCode, @NonNull String... perms) {
        if (perms.length <= 0)
            return;
        if (null == host.getContext())
            return;
        ArrayList<String> permList = new ArrayList<>();
        for (String perm : perms) {
            if (ActivityCompat.checkSelfPermission(host.getContext().getApplicationContext(), perm) != PackageManager.PERMISSION_GRANTED) {
                permList.add(perm);
            }
        }
        host.requestPermissions(permList.toArray(new String[permList.size()]), requestCode);
    }

    /**
     * Handle the result of a permission request, should be called from the calling {@link
     * Activity}'s {@link ActivityCompat.OnRequestPermissionsResultCallback#onRequestPermissionsResult(int,
     * String[], int[])} method.
     * <p>
     * If any permissions were granted or denied, the {@code object} will receive the appropriate
     * callbacks through {@link PermissionCallback} and methods annotated with {@link
     * PermissionCallback#onPermissionsFullGranted(int)} will be run if appropriate.
     *
     * @param requestCode  requestCode argument to permission result callback.
     * @param permissions  permissions argument to permission result callback.
     * @param grantResults grantResults argument to permission result callback.
     * @param receivers    an array of objects that have a method annotated with {@link
     *                     PermissionCallback#onPermissionsFullGranted(int)} or implement {@link PermissionCallback}.
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull Object... receivers) {
        // Make a collection of granted and denied permissions from the request.
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // iterate through all receivers
        for (Object object : receivers) {
            // Report granted permissions, if any.
            if (!granted.isEmpty()) {
                if (object instanceof PermissionCallback) {
                    ((PermissionCallback) object).onPermissionsGranted(requestCode, granted);
                }
            }

            // Report denied permissions, if any.
            if (!denied.isEmpty()) {
                if (object instanceof PermissionCallback) {
                    ((PermissionCallback) object).onPermissionsDenied(requestCode, denied);
                }
            }

            // If 100% successful, call annotated methods
            if (!granted.isEmpty() && denied.isEmpty()) {
                if (object instanceof PermissionCallback) {
                    ((PermissionCallback) object).onPermissionsFullGranted(requestCode);
                }
            }
        }
    }

    /**
     * Run permission callbacks on an object that requested permissions but already has them
     * by simulating {@link PackageManager#PERMISSION_GRANTED}.
     *
     * @param object      the object requesting permissions.
     * @param requestCode the permission request code.
     * @param perms       a list of permissions requested.
     */
    private static void notifyAlreadyHasPermissions(@NonNull Object object, int requestCode, @NonNull String[] perms) {
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;
        }

        onRequestPermissionsResult(requestCode, perms, grantResults, object);
    }
}
