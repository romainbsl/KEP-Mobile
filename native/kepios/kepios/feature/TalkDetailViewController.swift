//
//  TalkDetailViewController.swift
//  kepios
//
//  Created by Romain Bsl on 09/10/2019.
//  Copyright © 2019 Romain Bsl. All rights reserved.
//
import UIKit
import kepcommon

class TalkDetailViewController: UIViewController, TalkDetailView {
    var talkId : String = ""
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var speakerView: UIView!
    @IBOutlet weak var speakerImage0: UIImageView!
    @IBOutlet weak var speakerImage1: UIImageView!
    @IBOutlet weak var speakerImage2: UIImageView!
    @IBOutlet weak var speakerLabel: UILabel!
    @IBOutlet weak var roomLabel: UILabel!
    @IBOutlet weak var descriptionLabel: UILabel!
    
    private var presenter: TalkDetailPresenter!

    override func viewDidLoad() {
        super.viewDidLoad()
        initPresenter()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        presenter.attachView(view: self)
        presenter.getCurrentTalk(talkId: talkId)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        presenter.detachView()
    }
    
    private func initPresenter() {
        presenter = InjectorCommon.init().provideTalkDetailPresenter()
    }
    
    func onSuccessGetTalkDetail(talk: Talk) {
        titleLabel!.text = talk.title
        if talk.speakers.count > 0 {
            if talk.speakers.count > 1 {
                speakerImage1!.load(url: URL(string: "https://everywhere.kotlin.paris/speakers/"+talk.speakers[0].id+".jpg")!)
                speakerImage2!.load(url: URL(string: "https://everywhere.kotlin.paris/speakers/"+talk.speakers[1].id+".jpg")!)
                speakerImage0!.isHidden = true
            } else {
                speakerImage0!.load(url: URL(string: "https://everywhere.kotlin.paris/speakers/"+talk.speakers[0].id+".jpg")!)
                speakerImage1!.isHidden = true
                speakerImage2!.isHidden = true
            }
            speakerLabel!.text = talk.speakers.map({$0.name}).joined(separator: ", ")
        } else {
            speakerView!.isHidden = true
            speakerLabel!.isHidden = true
        }
        roomLabel!.text = talk.timeslot + " - " + talk.room
        descriptionLabel!.text = talk.content
    }
    
     
    
    func onFailureGetTalkDetail(e: KotlinException) {
//        print(e.message ?? "Error")
    }
    
    func checkInternetConnection() -> Bool {
        return true
    }
}

func maskRoundedImage(image: UIImage, radius: CGFloat) -> UIImage {
    let imageView: UIImageView = UIImageView(image: image)
    let layer = imageView.layer
    layer.masksToBounds = true
    layer.cornerRadius = radius
    UIGraphicsBeginImageContext(imageView.bounds.size)
    layer.render(in: UIGraphicsGetCurrentContext()!)
    let roundedImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    return roundedImage!
}

extension UIImageView {
    func load(url: URL) {
        DispatchQueue.global().async { [weak self] in
            if let data = try? Data(contentsOf: url) {
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self?.image = maskRoundedImage(image: image,radius: 100)
                    }
                }
            }
        }
    }
}
