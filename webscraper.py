import requests
from bs4 import BeautifulSoup
 

URL = "https://www.llm.gov.my/awam/cctv" # Replace this with the website's URL
getURL = requests.get(URL, headers={"User-Agent":"Mozilla/5.0"})
print(getURL.status_code)
soup = BeautifulSoup(getURL.text, 'html.parser')
 
images = soup.find_all('img')
print(images)