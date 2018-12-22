# Shopping List Desktop Application

## Introduction

This app was made as a school project for Object Oriented Programming Java course.

## Features

The application has basic functionalities to add and remove items. When adding items, the application uses EzParser, a JSON parsing library, to write the items to a JSON file. The application also reads the items from the JSON file.

On top of the JSON functionality, user can also save the items to a MySQL database and load the items also from it using Hibernate, though due to security and privacy concerns the MySQL database's password has been changed in the repository. So basically user would have to have his/her own MySQL database and edit hibernate.cfg.xml to use that instead.