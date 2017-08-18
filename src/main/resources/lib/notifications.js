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
 * @returns {Object} Returns an object with a new key pair.
 */
exports.generateKeyPair = function () {
    var bean = __.newBean('com.enonic.lib.notifications.KeyPairGenerationBean');
    var keyPairObj = bean.generateKeyPair();
    return __.toNativeObject(keyPairObj);
};

/**
 * Send a push notification to a client.
 *
 * @param {object} notification JSON with the parameters.
 * @param {string} notification.publicKey VAPID public key.
 * @param {string} notification.privateKey VAPID private key.
 * @param {string} notification.endpoint The Push service endpoint URL, received as part of the Subscription data.
 * @param {string} notification.auth The auth key received as part of the Subscription data.
 * @param {string} notification.receiverKey The p256dh key received as part of the Subscription data.
 * @param {[string|object]} notification.payload Message payload to send.
 * @returns {status} Response status from the HTTP request made. 200 if the notification is successfully sent.
 */
exports.send = function (notification) {
    var payload = notification.payload == null ? '' : notification.payload;
    if (typeof payload === 'object') {
        payload = JSON.stringify(payload);
    }

    checkRequired(notification, 'endpoint');
    checkRequired(notification, 'publicKey');
    checkRequired(notification, 'privateKey');
    checkRequired(notification, 'auth');
    checkRequired(notification, 'receiverKey');

    var pushBean = __.newBean('com.enonic.lib.notifications.PushBean');
    pushBean.publicKey = notification.publicKey;
    pushBean.privateKey = notification.privateKey;
    pushBean.endpoint = notification.endpoint;
    pushBean.auth = notification.auth;
    pushBean.receiverKey = notification.receiverKey;
    pushBean.payload = payload;

    return __.toNativeObject(pushBean.send());
};

function checkRequired(params, name) {
    if (params[name] === undefined) {
        throw "Parameter '" + name + "' is required";
    }
}
