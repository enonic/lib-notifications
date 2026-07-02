import type { ByteSource } from "@enonic-types/core";

declare module "/lib/notifications" {
    /**
     * A VAPID public/private key pair, in both Base64-URL (unpadded) and
     * binary-stream representations.
     */
    export interface KeyPair {
        /** Private key encoded as Base64-URL (unpadded). Must never be sent to the client. */
        privateKey: string;
        /** Public key encoded as Base64-URL (unpadded). */
        publicKey: string;
        /** Private key as a binary stream. */
        privateKeyBytes: ByteSource;
        /** Public key as a binary stream. */
        publicKeyBytes: ByteSource;
    }

    /**
     * Parameters shared by `send` and `sendAsync`.
     */
    export interface NotificationParams {
        /** VAPID public key (Base64-URL). */
        publicKey: string;
        /** VAPID private key (Base64-URL). */
        privateKey: string;
        /** The Push service endpoint URL, received as part of the Subscription data. */
        endpoint: string;
        /** The auth key received as part of the Subscription data. */
        auth: string;
        /** The p256dh key received as part of the Subscription data. */
        receiverKey: string;
        /** Message payload. Objects are JSON-stringified before being sent. */
        payload?: string | Record<string, unknown>;
        /** Optional VAPID "sub" claim — a `mailto:` or `https:` contact URI identifying the sender to the push service. */
        subject?: string;
    }

    /**
     * Parameters for `sendAsync`. Adds optional callbacks reporting the outcome.
     */
    export interface AsyncNotificationParams extends NotificationParams {
        /** Called with the HTTP status code when the push succeeds (2xx). */
        success?: (status: number) => void;
        /** Called with the HTTP status code on failure, or `0` if an exception was thrown. */
        error?: (status: number) => void;
    }

    /**
     * Generate a VAPID public/private key pair.
     *
     * @returns A new key pair with Base64-URL-encoded strings and their binary streams.
     */
    export function generateKeyPair(): KeyPair;

    /**
     * Send a push notification to a client synchronously.
     *
     * @param notification Push parameters.
     * @returns The HTTP status code from the push service response (2xx on success).
     */
    export function send(notification: NotificationParams): number;

    /**
     * Send a push notification to a client asynchronously.
     * Returns immediately; the result is reported via the optional `success` / `error` callbacks.
     *
     * @param notification Push parameters, plus optional `success` / `error` callbacks.
     */
    export function sendAsync(notification: AsyncNotificationParams): void;
}

export {};
