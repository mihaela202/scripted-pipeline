node{
    stage("Pull Repo"){
        git branch: 'solution', changelog: false, poll: false, url: 'https://github.com/mihaela202/terraform-vpc.git'
    }
    withCredentials([usernamePassword(credentialsId: 'jenkins-aws-access-key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        stage("Terrraform Init"){
            sh '''
                cd sandbox/
                terraform-0.13 version
                terraform-0.13 init
                terraform-0.13 plan -var-file dev.tfvars
            '''
        }
        stage("Terraform Apply"){
            sh '''
                terraform-0.13 apply -auto-approve
            '''
        }
    }    
}