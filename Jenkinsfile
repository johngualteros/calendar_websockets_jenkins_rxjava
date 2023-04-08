pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM',
                          branches: [[name: 'john_development']],
                          extensions: [[$class: 'PreBuildMerge',
                                        options: [mergeTarget: 'development']]],
                          userRemoteConfigs: [[url: 'https://github.com/johngualteros/calendar_websockets.git']]])
            }
        }
        // ... more stages here ...
    }
}
