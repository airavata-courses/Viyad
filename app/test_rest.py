import unittest
import rest


class test_rest(unittest.TestCase):
    
    def setUp(self):
        self.app = rest.app.test_client()
        self.app.testing = True

    def test_status_code(self):
        response = self.app.get('/')
        self.assertEqual(response.status_code, 200) 
    
    def test_message(self):
        response = self.app.get('/')
        message = b'{"Success":"Flask app running successfully"}\n'
        self.assertEqual(response.data, message)   

if __name__ == '__main__':
    unittest.main()

