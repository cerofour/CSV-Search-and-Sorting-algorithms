# Coding guidelines

[![sas.png](https://i.postimg.cc/4NcsH8Mt/sas.png)](https://postimg.cc/WhsQQ7sb)

## Classes

- A class should only do ONE thing and do it efficiently.
- Exceptions make code awful, so always encapsulate usages
of functions that throw exceptions to make it more readable.

## Variables

- All variables should be initialized EXPLICITLY, never leave any
variable IMPLICITLY initialized because that may lead to bugs.
