var testing = require('/lib/xp/testing.js');
var notificationsLib = require('/lib/notifications');


exports.testGenerateKeyPair = function () {

    var keyPair = notificationsLib.generateKeyPair();

    testing.assertNotNull(keyPair.privateKey);
    testing.assertNotNull(keyPair.publicKey);
    testing.assertNotNull(keyPair.privateKeyBytes);
    testing.assertNotNull(keyPair.publicKeyBytes);

};

exports.testSend = function () {

    var endpoint = 'https://fcm.googleapis.com/fcm/send/dttNMLkH4jg:APA91bGpKkDCoJe65norwN1gTlnDAh7rDyFA-eoLPd99fjz7QODksnfdZqg_YefPXsbWmftbAKwbWn-a5WGUrGuQvBKV6dwRlSMu8IVyx5yGbF8ST3H3Q4XASwjRXHmF8V-dS7nhEwxF';
    var status = notificationsLib.send({
        privateKey: 'SmItipweO3J9uzT927AhxVBtBvmJFk4CGD2Y7e5Qjnk',
        publicKey: 'BBfc7IpYHxS2OE2mX8L7XWYrqSBrAPYhZMjN5_Eszb3sWzlEGBzq1U4bupGAuuyD9Y9u2lmgCU2VmdP1H8iJsuw',
        endpoint: endpoint,
        auth: 'tkCu6tlXTgcWMVgW7z9qQA==',
        receiverKey: 'BKHSDNB3huPof8GdBXZNKePUrFL4NNabghAHvZqtP0oqJ0rJRgGn4RKKJ-sQtGLphyRfJsUEz9qJWEYeKXXiei4=',
        payload: {
            message: 'Hello'
        }
    });

    testing.assertNotNull(status);
    testing.assertTrue(typeof status === 'number');

};
