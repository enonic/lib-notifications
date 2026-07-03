# lib-notifications TS types

Type definitions for the [Enonic XP Notifications library](https://github.com/enonic/lib-notifications).

## Install

```bash
npm i --save-dev @enonic-types/lib-notifications
```

## Use

Add the corresponding types to your `tsconfig.json` file that is used for application's server-side TypeScript code:

```json
{
  "compilerOptions": {
    "paths": {
      "/lib/notifications": ["./node_modules/@enonic-types/lib-notifications"],
    },
  }
}
```

## Example

```ts
import {generateKeyPair, send} from '/lib/notifications';

const keyPair = generateKeyPair();

const status = send({
    publicKey: keyPair.publicKey,
    privateKey: keyPair.privateKey,
    endpoint: subscription.endpoint,
    auth: subscription.auth,
    receiverKey: subscription.receiverKey,
    payload: {message: 'Hello!'},
});
```
