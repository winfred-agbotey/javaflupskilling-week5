pipeline{
	agent any

	environment{
		//Define environment variables here
		MY_ENV_VAR = 'Custom Value'
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
						userRemoteConfigs: [[url: gitRepoUrl]],
						extensions: [[$class: 'CleanBeforeCheckout'],[$class: 'CloneOption', noTags: false, shallow: true, depth: 1]],
              		])
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
		stage('Deploy'){
			steps{
				//Deploy your application to a target environment (e.g, staging, production)
				sh 'echo "Value of ENV Variable is "$MY_ENV_VAR""'
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