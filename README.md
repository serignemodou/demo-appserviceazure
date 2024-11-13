# Files configuration
1- Pom.xml, the spring boot config file that content app insights package version and others
2- applicationinsights.json, the app insights config file, that content the sampling config
3- DemoApplication.jave, the file that contains the logic code.


# Connect the app to azure app insights
1- Create azure app insights service
2- Copy the IntrumentationKey and past it in the connectionString field on applicationinsights.json file

# Déploy app to azure app service
1- Create azure app service with this app insights
2- Create github action workflow to automate the deployment (optional)

# Access to the application
1- Use the path endpoint in the DemoApplication.java file example
1-1: <app-serivice-custom-domain>/app/v1/parcours/utilisateurs
1-2: <app-serivice-custom-domain>/app/v1/health-check/test

# Verify if the sampling override is apply
1- Verify if only requests with path parcours are logged in the app insights requests logs table

# More info
To see the attributes in the app service logs, i added this env variable on azure app service
APPLICATIONINSIGHTS_SELF_DIAGNOSTICS_LEVEL=DEBUG