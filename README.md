# Project: The 3-tab project

Members: 황소진, 김재민

Summary:

Tab 1: Contacts
json file에 있는 contact 정보 읽어오기
Recyclerview를 통해 contact item 띄우기
Filterable interface implement해서 filter method를 이용한 search 기능 구현
Item 선택 시 intent를 통해 itemActivity로 전환
-> itemActivity에서 전화 버튼을 통해 dial application으로 전환
Item에 있는 delete 버튼에 대해 contact 삭제 기능 구현
하단의 FloatingActionButton으로 contact add기능 구현
-> button 선택 시 AddActivity로 전환 
-> AddActivity에서 name과 number입력 후 onActivityResult method를 통해 result Intent받아서 number_list에 추가



Tab 2: Gallery
CAMERA, WRITE_EXTERNAL_STORAGE permission 요청
spinner를 통해 internal memory directories 중 선택
gridView를 통해 directory의 사진을 출력
이미지 로딩 도중 progressBar 애니메이션 출력
gridView의 OnItemClickListener로 클릭 시 해당 사진을 ImageView에 출력
GridImageAdapter class와 UniversalImageLoader 사진들을 gridView에 띄움
사진 규격이 제각각 -> SquareImageView class를 통하여 사진 크기를 통일하여 gridView에 출력
FloatingActionButton 으로 카메라 버튼을 만듦: 클릭시 카메라 Intent를 만들어 카메라 앱을 엶, 촬영한 사진은 Pictures 폴더에 이동


Tab 3: Coin-flipper
동전의 각 면당 1개의 imageView를 만들고, 여기에 OnClickListener로 동전을 던지는 애니메이션을 재생
SwapViews, DisplayNextView, Flip3dAnimation 등의 헬퍼 클래스를 참고하여 모션 구현
Coin flip 결과를 Toast message로 출력
