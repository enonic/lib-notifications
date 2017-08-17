/**
 * Push notifications related functions.
 *
 * @example
 * var notificationsLib = require('/lib/notifications');
 *
 * @module notifications
 */

/**
 * Generate a VAPID public/private key pair.
 *
 * @returns {Object} Returns a new key pair.
 */
exports.generateKeyPair = function () {
    var bean = __.newBean('com.enonic.lib.notifications.KeyPairGenerationBean');
    var keyPairObj = bean.generateKeyPair();
    return __.toNativeObject(keyPairObj);
};
