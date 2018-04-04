Current status: Elytron is hard, yo

This is the "Basic Auth" example from the website. Looks like it doesn't touch your program at all, it goes through Elytron "realms"
We want something like a PicketLink "Identity" so that you can still be a user

PicketLink -> *KeyCloak*, not directly Elytron??

- "application-security-domain" may be a programmatic one instead

```mvn wildfly:deploy```