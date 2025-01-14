pipeline{
	agent any

	environment{
		//Define environment variables here
		MY_ENV_VAR = 'Custom Value'
		DB_URL = credentials('DB_URL')
		POSTGRES_DB = credentials('POSTGRES_DB')
		DB_PASSWORD = credentials('DB_PASSWORD')
		DB_USERNAME = credentials('DB_USERNAME')
		DOCKERHUB_CREDENTIALS_PSW = credentials('DOCKERHUB_CREDENTIALS_PSW') // Jenkins credentials ID for Docker Hub
		DOCKERHUB_CREDENTIALS_USR = credentials('DOCKERHUB_CREDENTIALS_USR') // Jenkins credentials ID for Docker Hub
        DOCKER_IMAGE = 'mawulidev/javaflupskilling-week5' // Replace with your Docker Hub username and repository name
        DOCKER_TAG = 'latest' // Tag for your Docker image
	}
	stages{
		stage('Checkout'){
			steps{
				// Checkout your source code from your version control system(e.g, Git)
				//checkout scm
				script{
					//Clone the Git repository's master branch
					def gitRepoURL = 'https://github.com/winfred-agbotey/javaflupskilling-week5.git'

					checkout([$class: 'GitSCM',
						branches: [[name: '*/main']],
						userRemoteConfigs: [[url: 'https://github.com/winfred-agbotey/javaflupskilling-week5.git']],
						extensions: [[$class: 'CleanBeforeCheckout'],[$class: 'CloneOption', noTags: false, shallow: true, depth: 1]],
              		])
				}
			}

		}
		stage('Prepare Environment'){
			steps{
				configFileProvider([configFile(fileId: 'env-file', targetLocation: '.env')]){
					sh 'cat .env' // Optional: Verify that the file is placed
                }
                }

		}
		stage('Build'){
			steps{
				//Build your application here (e.g, compile package etc)
				//	sh 'your build command'
				sh '''
				ls
				echo "In Build Step"
				mvn clean install
				'''
				}
		}
		stage('Test'){
			steps{
				//Run your test (e.g, unit tests, integration tests)
				sh '''
				echo "In Test Step"
				mvn test
				'''
			}
		}
		stage('Docker Build and Push'){
			steps{
				withCredentials([string(credentialsId: 'DOCKERHUB_CREDENTIALS_USR', variable: 'DOCKERHUB_CREDENTIALS_USR'), string(credentialsId: 'DOCKERHUB_CREDENTIALS_PSW', variable: 'DOCKERHUB_CREDENTIALS_PSW')]) {
					// some block
				sh """
				echo "Building Docker image"
				docker build -t $DOCKER_IMAGE:$DOCKER_TAG .
				"""
				sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"
                // Push Docker image
                 sh """
                 echo "Pushing Docker image to Docker Hub..."
                 docker push $DOCKER_IMAGE:$DOCKER_TAG
                 """
				}
			}
		}
	}

	post{
		success{
			//Action to perform when the pipeline succeeds
			echo 'Pipeline succeeded!'
		}
		failure{
			//Actions to perform when the pipeline fails
			echo 'Pipeline failed!'
		}
	}
}