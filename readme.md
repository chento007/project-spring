1. register https://photostad.istad.co/api/v1/auth/register
2. verify with email https://photostad.istad.co/api/v1/auth/verify?email={{email}}
3. check verify to homepage https://photostad.istad.co/api/v1/auth/check-verify (param email and verifiedCode)
4. when go watermark or certificate , if not yet login go to log in
5. login https://photostad.istad.co/api/v1/auth/login (require email and password body json)
6. refresh jwt https://photostad.istad.co/api/v1/auth/refresh (require token)
   ===================================================================

1. watermark https://photostad.istad.co/watermark
2. get json from front-end https://photostad.istad.co/api/v1/watermarks/download-zip
3. generate image https://photostad.istad.co/api/v1/watermarks/download-zip
4. response url to download https://photostad.istad.co/api/v1/watermarks/download-zip
5. download
   zip https://photostad.istad.co/api/v1/watermark-downloads/download-watermark/f476ccb0-4620-4de8-b920-58767e7bf445

   ===================================================================

# export excel

1. certificate https://photostad.istad.co/api/v1/certificate
2. export certificate is export  https://photostad.istad.co/api/v1/certificates/export
3. get json https://photostad.istad.co/api/v1/certificates/export
4. generate excel with column name https://photostad.istad.co/api/v1/certificates/export
5. response url to download
   excel https://photostad.istad.co/api/v1/certificates/download-excel/c9177292-12d4-41b8-b7de-2cbafe303abe.xlsx

# import excel

1. upload excel after user fill in
2. upload folder image optional https://photostad.istad.co/api/v1/certificates/{featureID}/import-excel
3. put feature ID to get sample to generate https://photostad.istad.co/api/v1/certificates/{featureID}/import-excel
4. compare folder image with photo in Excel https://photostad.istad.co/api/v1/certificates/{featureID}/import-excel
5. response json that after put from sample https://photostad.istad.co/api/v1/certificates/{featureID}/import-excel
6. when user click download : progressing ...
7. compress all scene : progressing ...
8. download : progressing ...
===================================================================

database :
host : 136.228.158.126
port : 5435
imital database : photostad
username : photostad
password : photostad4444

===================================================================

api github : https://github.com/cstadservice/photostad-api.git
postman : https://app.getpostman.com/join-team?invite_code=e7af87b7513b1a22de7e061b2830d702&target_code=5920f9d8f6e6f508920ef9b0e133fb84


==================================================================
noted for base-rest :
dev : http://localhost:8080/api/v1/
stage : http://136.228.158.126:8002/api/v1/
pro : https://photostad.istad.co/api/v1/
===================================================================

# end point 
# certificate
- insert : {{base-rest}}certificates
- searchById : {{base-rest}}certificates/8
- deletwById : {{base-rest}}certificates/2?
- findALl : {{base-rest}}certificates?page=1&limit=20
- updateCertificate : {{base-rest}}certificates/4
- exportCertificate : {{base-rest}}certificates/export
- importExcel : {{base-rest}}certificates/17/import-excel
# auth
- register : {{base-rest}}auth/register
- verify : {{base-rest}}auth/verify?email=jih@gmail.com
- login : {{base-rest}}auth/login
- refreshToken : {{base-rest}}auth/refresh
- checkVerify : {{base-rest}}auth/check-verify
- getDashboardProfile : {{base-rest}}auth/dashboard/me
- getProfileClient : {{base-rest}}auth/me
- verifyForgotPassword : {{base-rest}}auth/verify-forgot-password?email=chentochea2002@gmail.com
- checkVerifyWithForgotPassword : {{base-rest}}auth/check-verify-forgot-password
- resetPasswordWithForgotPassword : {{base-rest}}auth/reset-password
- loginWithGoogle : {{base-rest}}auth/register-with-google
- generateUUID : {{base-rest}}auth/generate-uuid
- findUserByUUID : {{base-rest}}auth/check-uuid/79985be1-5915-4b8a-9772-b97c2d6c145b
- findEmail : {{base-rest}}auth/email/chent2ochea2002@gmail.com
- updateProfileAdmin : {{base-rest}}auth/update-profile/1
# user
- findEmail : {{base-rest}}users/email?email=suju@gmail.com
- findAll : {{base-rest}}users?page=1&limit=20&name=Chento Smos
- deleteById : {{base-rest}}users/11
- updateUser : {{base-rest}}users/270
- find by id : {{base-rest}}users/20
- createUser : {{base-rest}}users
- changePassword : {{base-rest}}users/05f209fb-b0a3-4894-881c-93f566390d9c/change-password
- update-profile :  {{base-rest}}users/20/update-profile
- updateProfileClient : {{base-rest}}users/cbeb38c1-3344-440f-92df-254f02e5df05/update-profile-client
- update-information-client : {{base-rest}}users/cbeb38c1-3344-440f-92df-254f02e5df05/update-information-client
- changePasswordAdmin : {{base-rest}}users/123/change-password-user

#Image
- findEmail : {{base-rest}}images?page=1&limit=10&type=st5y46eu57ij87k9
- findAll : {{base-rest}}images?page=1&limit=10
- insertImage : {{base-rest}}images
- findById : {{base-rest}}images/59
- deleteById : {{base-rest}}images/1
- updateById : {{base-rest}}images/1

#file
- findAll : {{base-rest}}files
- uploadBase64 : {{base-rest}}files/upload-file-base64
- uploadSingle : {{base-rest}}files
- uploadMultiple : {{base-rest}}files/multiple?files
- findByName : {{base-rest}}files/c07198c8-06cc-40c7-9379-ec1be2cd2e66.jpg
- removeAll : {{base-rest}}files
- uploadFolder : {{base-rest}}files/upload-folder?files

# Tutorials

- insertTutorail : {{base-rest}}tutorials
- findById : {{base-rest}}tutorials/131
- deleteById : {{base-rest}}tutorials/2
- updateTutorial :  {{base-rest}}tutorials/3
- selectTutorialCount : {{base-rest}}tutorials/tutorial-count
- findTutorialByUUID : {{base-rest}}tutorials/front/8fd1e766-8d4d-497b-851a-34cab57bd6f9
- finndALlTutorialWithFront : {{base-rest}}tutorials/front/
- updateViewCount : {{base-rest}}tutorials/update-view-count/8fd1e766-8d4d-497b-851a-34cab57bd6f9

# Request tutorial

- insertRequestTutorial : {{base-rest}}request-tutorials
- findById : {{base-rest}}request-tutorials/35
- findAll : {{base-rest}}request-tutorials?isDeleted=false&isRead=false&limit=3&page=5
- update : {{base-rest}}request-tutorials/1
- findUnread : {{base-rest}}request-tutorials/is-read?isRead=true&page=2&isDeleted=false&limit=2
- deleteRequestTutorialById : {{base-rest}}request-tutorials/
- user-request : {{base-rest}}request-tutorials/user-request
- update-status : {{base-rest}}request-tutorials/23/true/update-status

# feature

- insert : {{base-rest}}features
- findById : {{base-rest}}features/7
- findAll : {{base-rest}}features
- update : {{base-rest}}features/12

# watermark

- findAll : {{base-rest}}watermarks?page=1&limit=20
- insertWatermark : {{base-rest}}watermarks
- findById : {{base-rest}}watermarks/2
- deleteById : {{base-rest}}watermarks/2
- update : {{base-rest}}watermarks/1
- dailyEdit : {{{base-rest}}watermarks/daily-edit
-
# Watermark download

- insertWatermarkDownload : {{base-rest}}watermark-downloads
- findById : {{base-rest}}watermark-downloads/1
- findAll : {{base-rest}}watermark-downloads
- deleteById : {{base-rest}}watermark-downloads/1
- updateById : {{base-rest}}watermark-downloads/1


# Certificate Download

- insertCertificateDownload : {{base-rest}}certificate-downloads
- findById : {{base-rest}}certificate-downloads/2
- findAll : {{base-rest}}certificate-downloads
- certificate-download : {{base-rest}}certificate-downloads/2
- deleteById : {{base-rest}}certificate-downloads/2


#role

- selectAllRole : {{base-rest}}roles
- updateRole : {{base-rest}}roles/1
- selectById : {{base-rest}}roles/1
- allrole : {{base-rest}}roles

# report and statistic

- static : {{base-rest}}statistics
- generateCertificate : {{base-rest}}statistics/certificate
- generateWatermark : {{base-rest}}statistics/watermarks
- certificateDownload : {{base-rest}}statistics/certificate-download?page=1&limit=10
- watermarkDownload : {{base-rest}}statistics/watermark-download?page=1&limit=10

# tutorial management

- tutorialManagement : {{base-rest}}tutorial-managements?isRead=true&limit=40&page=1
- configSEO : {{base-rest}}tutorial-managements/44/config-seo
- unread : {{base-rest}}tutorial-managements
- findSEOByID : {{base-rest}}tutorial-managements/1

# dashboard overview
- selectDashboardOverview : {{base-rest}}dashboard-overviews

# process watermark

- uplaod folder : {{base-rest}}files/upload-folder?files
- generate watermark : {{base-rest}}watermarks/generate-watermark
-
# process certificate

- exportExcellGenerate : {{base-rest}}certificates/export
- insertFeature : {{base-rest}}features
- importExcel : {{base-rest}}certificates/168/import-excel
- generate certificate : {{base-rest}}certificates/generate-certificate/PDF

# uplaod folder

- upload profile : {{base-rest}}files
- insert into database : {{base-rest}}images

# contact us

- save : {{base-rest}}contact-us
- findall : {{base-rest}}contact-us


 



