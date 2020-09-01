properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'nodeIP', trim: true)
        ])
    ])

node {
    
    stage ('Pull the repo'){
        git  url: 'https://github.com/ikambarov/spring-petclinic.git'
    }
    stage ( "change dir"){
        sh 'cd ../spring-petclinic/'
    }
    stage ( "install maven"){
        sh './mvnw package'
    }

    stage ( 'install java') {
        sh 'java -jar target/*.jar'
    }
}