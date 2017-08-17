var assert = require('/lib/xp/assert');
var notificationsLib = require('/lib/notifications');


exports.testGenerateKeyPair = function (mockServer) {

    var keyPair = notificationsLib.generateKeyPair();

    print(JSON.stringify(keyPair));

    assert.assertNotNull(keyPair.privateKey);
    assert.assertNotNull(keyPair.publicKey);
    assert.assertNotNull(keyPair.privateKeyB64);
    assert.assertNotNull(keyPair.publicKeyB64);

};